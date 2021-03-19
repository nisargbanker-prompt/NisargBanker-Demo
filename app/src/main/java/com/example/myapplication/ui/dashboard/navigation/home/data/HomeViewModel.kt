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
    val characters = repository.getUsers()
    init {
        isLoading.value = true
    }
}