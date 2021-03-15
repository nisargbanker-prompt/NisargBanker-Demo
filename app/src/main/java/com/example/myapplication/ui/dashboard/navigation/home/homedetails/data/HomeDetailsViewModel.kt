package com.example.myapplication.ui.dashboard.navigation.home.homedetails.data

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repositories.UserRepository
import com.example.myapplication.ui.dashboard.navigation.home.data.DataItem
import com.example.myapplication.ui.dashboard.navigation.home.data.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeDetailsViewModel @ViewModelInject constructor(private val repository: UserRepository) : ViewModel() {

    var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = true
    }
    var responseFromNetwork: MutableLiveData<UserDetailsResponse> = MutableLiveData<UserDetailsResponse>()
    var errorFromNetwork = MutableLiveData<String>()
    var userId = ""

    fun getUserData(){
        viewModelScope.launch {
            val authResponse = userDetails(userId)
            responseFromNetwork.value =  authResponse
            isLoading.value = false
        }
    }

    suspend fun userDetails(userId: String) = withContext(Dispatchers.IO) { repository.userDetails(userId) }
}