package com.example.wonderland.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wonderland.Models.AttractionTicketRequestModel
import com.example.wonderland.Models.EventTicketRequestModel
import com.example.wonderland.Models.TicketModel
import com.example.wonderland.attractionTicketService
import com.example.wonderland.eventTicketService
import kotlinx.coroutines.launch

class EventTicketViewModel(): ViewModel() {
    private val _ticketsState = mutableStateOf(TicketsState())
    val ticketsState: State<TicketsState> = _ticketsState

    fun fetchTickets(token: String?, sort: Int) {
        viewModelScope.launch {
            try{
                val response = eventTicketService.getAllEventTickets(token, sort)
                _ticketsState.value = _ticketsState.value.copy(
                    list = response,
                    loading = false,
                    error = null
                )
            } catch(e: Exception) {
                _ticketsState.value = _ticketsState.value.copy(
                    loading = false,
                    error = "Error fetching event tickets ${e.message}"
                )
            }
        }
    }

    fun addTicket(token: String?, eventId: Int, isExpired: Boolean = false) {
        viewModelScope.launch {
            try {
                eventTicketService.addEventTicket(
                    token,
                    EventTicketRequestModel(eventId, isExpired)
                )
            } catch(_: Exception) {

            }
        }
    }

    data class TicketsState(
        var loading: Boolean = true,
        val list: List<TicketModel> = emptyList(),
        val error: String? = null
    )
}