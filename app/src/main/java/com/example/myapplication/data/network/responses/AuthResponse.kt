package com.example.myapplication.data.network.responses


data class AuthResponse(
    val isSuccessful : Boolean?,
    val error: String?,
    val token: String?
)