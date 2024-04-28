package com.example.wonderland.Models

import java.time.LocalDateTime

data class EventModel(
    val id: Int,
    val name: String,
    val description: String,
    val start_at: LocalDateTime,
    val end_at: LocalDateTime,
    val price: Int,
    val photo_url: String,
    val location_id: Int
)

data class EventResponseModel(
    val id: Int,
    val name: String,
    val description: String,
    val start_at: String,
    val end_at: String,
    val price: Int,
    val photo_url: String,
    val location_id: Int
)
data class EventDetailResponseModel(
    val id: Int,
    val name: String,
    val description: String,
    val start_at: String,
    val end_at: String,
    val price: Int,
    val photo_url: String,
    val location: String
)
