package com.example.wonderland.Models

import java.time.LocalDateTime

data class SessionModel(
    val id: Int,
    val userId: Int,
    val accessToken: String,
    val expiresAt: LocalDateTime
)