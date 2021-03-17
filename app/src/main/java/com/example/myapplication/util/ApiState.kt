package com.example.myapplication.util

import com.example.myapplication.data.network.responses.AuthResponse

sealed class ApiState {
    class Success(val authResponse: AuthResponse) : ApiState()
    data class Error(val message: String) : ApiState()
    object Loading : ApiState()
    object Empty : ApiState()
}