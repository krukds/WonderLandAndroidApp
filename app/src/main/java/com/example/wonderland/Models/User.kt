package com.example.wonderland.Models

data class UserModel(
    val id: Int,
    val email: String,
    val password: String,
    val first_name: String,
    val last_name: String,
    val phone: String,
)

data class UserRequestModel(
    val email: String,
    val password: String,
    val first_name: String,
    val last_name: String,
    val phone: String,
)