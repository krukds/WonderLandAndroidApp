package com.example.wonderland.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wonderland.Models.AttractionTicketRequestModel
import com.example.wonderland.Models.TicketModel
import com.example.wonderland.attractionTicketService
import kotlinx.coroutines.launch

class AttractionTicketViewModel(): ViewModel() {
    private val _ticketsState = mutableStateOf(TicketsState())
    val ticketsState: State<TicketsState> = _ticketsState

    fun fetchTickets(token: String?, sort: Int) {
        viewModelScope.launch {
            try{
                val response = attractionTicketService.getAllAttractionTickets(token, sort)
                _ticketsState.value = _ticketsState.value.copy(
                    list = response,
                    loading = false,
                    error = null
                )
            } catch(e: Exception) {
                _ticketsState.value = _ticketsState.value.copy(
                    loading = false,
                    error = "Error fetching attraction tickets ${e.message}"
                )
            }
        }
    }

    fun addTicket(token: String?, attractionId: Int, isExpired: Boolean = false) {
        viewModelScope.launch {
            try {
                attractionTicketService.addAttractionTicket(
                    token,
                    AttractionTicketRequestModel(attractionId, isExpired)
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