package com.example.wonderland.Models

data class AttractionModel(
    val id: Int,
    val locationId: Int,
    val name: String,
    val description: String,
    val price: Int,
    val duration: Int,
    val minimum_height: Double,
    val photo_url: String
)

data class AttractionDetailResponse(
    val id: Int,
    val location: String,
    val name: String,
    val description: String,
    val price: Int,
    val duration: Int,
    val minimum_height: Float,
    val photo_url: String,
    val tags: List<String>,
    val ages: List<String>
)
