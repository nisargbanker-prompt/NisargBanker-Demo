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
import com.example.myapplication.util.ValidationState
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

    private val _validationState = MutableStateFlow<ValidationState>(ValidationState.Empty)
    val validationState: StateFlow<ValidationState> = _validationState

    fun validation(view: View){
        var validationState: ValidationState = ValidationState.Empty
        validationState = when {
            email.get() == "" -> {
                ValidationState.Error("Please insert email")
            }
            password.get() == "" -> {
                ValidationState.Error("Please enter password")
            }
            confirmPassword.get() == "" -> {
                ValidationState.Error("Please enter confirm password")
            }
            password.get() != confirmPassword.get() -> {
                ValidationState.Error("Password and Confirm password not matched, please try again")
            }
            else -> {
                onRegisterHandler(view)
                ValidationState.Success
            }
        }
        _validationState.value = validationState
    }

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
                _registerUiState.value = ApiState.Error(e.message.toString())
            } catch (e: NoInternetException) {
                e.printStackTrace()
                _registerUiState.value = ApiState.Error(e.message.toString())
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