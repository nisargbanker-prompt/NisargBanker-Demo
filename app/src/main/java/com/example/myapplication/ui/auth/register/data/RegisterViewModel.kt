package com.example.myapplication.ui.auth.register.data

import android.view.View
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.myapplication.data.repositories.UserRepository
import com.example.myapplication.util.ApiException
import com.example.myapplication.util.ApiState
import com.example.myapplication.util.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _registerUiState = MutableStateFlow<ApiState>(ApiState.Empty)
    val registerUiState: StateFlow<ApiState> = _registerUiState

    fun onRegisterHandler(view: View) {
        viewModelScope.launch {
            try {
                _registerUiState.value = ApiState.Loading
                val authResponse = userRegister("eve.holt@reqres.in", "cityslicka")
                if (authResponse.token != null) {
                    _registerUiState.value = ApiState.Success(authResponse)
                } else {
                    _registerUiState.value = ApiState.Error(authResponse.error.toString())
                }
            } catch (e: ApiException) {
                e.printStackTrace()
                _registerUiState.value = ApiState.Error(e.printStackTrace().toString())
            } catch (e: NoInternetException) {
                e.printStackTrace()
                _registerUiState.value = ApiState.Error(e.printStackTrace().toString())
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