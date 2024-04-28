package com.example.wonderland.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wonderland.Models.EventDetailResponseModel
import com.example.wonderland.Models.EventModel
import com.example.wonderland.eventsService
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EventViewModel: ViewModel() {
    private val _eventsState = mutableStateOf(EventsState())
    val eventsState: State<EventsState> = _eventsState

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    init{
        fetchEvents()
    }

    suspend fun getEventById(id: Int): EventDetailResponseModel {
        return eventsService.getEventById(id)
    }

    private fun fetchEvents() {
        viewModelScope.launch {
            try {
                val response = eventsService.getAllEvents()
                val events = response.map { eventJson ->
                    EventModel(
                        id = eventJson.id,
                        name = eventJson.name,
                        description = eventJson.description,
                        start_at = LocalDateTime.parse(eventJson.start_at, formatter),
                        end_at = LocalDateTime.parse(eventJson.end_at, formatter),
                        price = eventJson.price,
                        photo_url = eventJson.photo_url,
                        location_id = eventJson.location_id
                    )
                }
                _eventsState.value = _eventsState.value.copy(
                    loading = false,
                    list = events,
                    error = null
                )

            } catch (e: Exception) {
                _eventsState.value = _eventsState.value.copy(
                    loading = false,
                    error = "Error fetching events ${e.message}"
                )
            }
        }
    }

    data class EventsState(
        var loading: Boolean = true,
        val list: List<EventModel> = emptyList(),
        val error: String? = null
    )
}