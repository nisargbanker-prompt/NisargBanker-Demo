package com.example.myapplication.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repositories.UserRepository
import com.example.myapplication.ui.auth.login.data.LoginViewModel
import com.example.myapplication.ui.auth.register.data.RegisterViewModel
import com.example.myapplication.ui.dashboard.navigation.home.data.HomeViewModel
import com.example.myapplication.ui.dashboard.navigation.home.homedetails.data.HomeDetailsViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(
                repository as UserRepository
            ) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(
                repository as UserRepository
            ) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(
                repository as UserRepository
            ) as T
            modelClass.isAssignableFrom(HomeDetailsViewModel::class.java) -> HomeDetailsViewModel(
                repository as UserRepository
            ) as T
            else -> throw IllegalArgumentException("ViewModelClass Not Found")
        }

    }
}