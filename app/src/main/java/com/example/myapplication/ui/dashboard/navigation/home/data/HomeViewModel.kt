package com.example.myapplication.ui.dashboard.navigation.home.data

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel @ViewModelInject constructor(private val repository: UserRepository) : ViewModel() {

    var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var responseFromNetwork: MutableLiveData<UserResponse> = MutableLiveData<UserResponse>()
    var errorFromNetwork = MutableLiveData<String>()

    init {
        isLoading.value = true
        viewModelScope.launch {
            val authResponse = users("1")
            responseFromNetwork.value =  authResponse
            isLoading.value = false
        }
    }

    suspend fun users(pageNumber: String) = withContext(Dispatchers.IO) { repository.users(pageNumber) }
}