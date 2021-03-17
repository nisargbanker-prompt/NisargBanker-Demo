package com.example.myapplication.ui.auth.login.data

import android.view.View
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.data.repositories.UserRepository
import com.example.myapplication.util.ApiException
import com.example.myapplication.util.ApiState
import com.example.myapplication.util.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel @ViewModelInject constructor(
    private val repository: UserRepository
) : ViewModel() {

    val email = ObservableField("")
    val password = ObservableField("")
    var isLoading = MutableLiveData<Boolean>().apply {
        value = false
    }

    private val _loginUiState = MutableStateFlow<ApiState>(ApiState.Empty)
    val loginUiState: StateFlow<ApiState> = _loginUiState

    fun onLoginHandler(view: View) {
        viewModelScope.launch {
            try {
                _loginUiState.value = ApiState.Loading
                val authResponse = userLogin("eve.holt@reqres.in", "cityslicka")
                if (authResponse.token != null) {
                    _loginUiState.value = ApiState.Success(authResponse)
                } else {
                    _loginUiState.value = ApiState.Error(authResponse.error.toString())
                }
            } catch (e: ApiException) {
                e.printStackTrace()
                _loginUiState.value = ApiState.Error(e.toString())
            } catch (e: NoInternetException) {
                e.printStackTrace()
                _loginUiState.value = ApiState.Error(e.toString())
            }
        }
    }

    fun onRegisterHandler(view: View){
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
    }

    suspend fun userLogin(
        email: String,
        password: String
    ) = withContext(Dispatchers.IO) { repository.userLogin(email, password) }

}