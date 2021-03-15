package com.example.myapplication.ui.auth.register.data

import android.view.View
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.myapplication.data.network.responses.AuthResponse
import com.example.myapplication.data.repositories.UserRepository
import com.example.myapplication.util.ApiException
import com.example.myapplication.util.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel @ViewModelInject constructor(
    private val repository: UserRepository
) : ViewModel() {

    val email = ObservableField("")
    val password = ObservableField("")
    val confirmPassword = ObservableField("")
    var isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }
    var responseFromNetwork: MutableLiveData<AuthResponse> = MutableLiveData<AuthResponse>()
    var errorFromNetwork = MutableLiveData<String>()

    fun onRegisterHandler(view: View) {
        isLoading.value = true
        viewModelScope.launch {
            try {
                val authResponse = userRegister("eve.holt@reqres.in", "cityslicka")
                isLoading.value = false
                if (authResponse.token != null) {
                    responseFromNetwork.value = authResponse
                } else {
                    errorFromNetwork.value = authResponse.error
                }
            } catch (e: ApiException) {
                e.printStackTrace()
                isLoading.value = false
            } catch (e: NoInternetException) {
                e.printStackTrace()
                isLoading.value = false
            }
        }
    }

    fun onBackHandler(view: View){
        Navigation.findNavController(view).navigateUp()
    }

    suspend fun userRegister(
        email: String,
        password: String
    ) = withContext(Dispatchers.IO) { repository.userSignup(email, password) }

}