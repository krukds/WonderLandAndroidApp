package com.example.wonderland.Models

import java.time.LocalTime

data class RestaurantModel(
    val id: Int,
    val name: String,
    val phone: String,
    val description: String,
    val location_id: Int,
    val open_at: LocalTime,
    val close_at: LocalTime,
    val menu_url: String,
    val main_photo: String
)

data class RestaurantResponseModel(
    val id: Int,
    val name: String,
    val phone: String,
    val description: String,
    val location_id: Int,
    val open_at: String,
    val close_at: String,
    val menu_url: String,
    val main_photo: String
)

data class RestaurantDetailResponse(
    val id: Int,
    val name: String,
    val phone: String,
    val description: String,
    val location: String,
    val open_at: String,
    val close_at: String,
    val menu_url: String,
    val photos: List<String>,
    val cuisines: List<String>
)