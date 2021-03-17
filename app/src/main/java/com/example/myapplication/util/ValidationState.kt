package com.example.myapplication.util

sealed class ValidationState {
    object Success : ValidationState()
    data class Error(val message: String) : ValidationState()
    object Empty : ValidationState()
}