package com.example.wonderland.Models

data class SignupRequestModel(
    val email: String,
    val password: String,
    val first_name: String,
    val last_name: String,
    val phone: String
)