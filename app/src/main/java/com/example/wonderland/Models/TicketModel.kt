package com.example.wonderland.Models


data class TicketModel(
    val id: Int,
    val title: String,
    val is_expired: Boolean,
    val created_at: String
)

data class AttractionTicketRequestModel(
    val attraction_id: Int,
    val is_expired: Boolean
)

data class EventTicketRequestModel(
    val event_id: Int,
    val is_expired: Boolean
)