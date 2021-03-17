package com.example.myapplication.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.ui.auth.login.data.LoginViewModel
import com.example.myapplication.ui.dashboard.MainActivity
import com.example.myapplication.util.ApiState
import com.example.myapplication.util.ValidationState
import com.example.myapplication.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        observeValidation()
        return binding.root
    }

    private fun observeValidation(){
        lifecycleScope.launchWhenStarted {
            viewModel.validationState.collect {
                when(it){
                    is ValidationState.Success -> {
                        obeserveStateFlow()
                    }
                    is ValidationState.Error -> {
                        toast(this@LoginFragment.requireContext(), it.message)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun obeserveStateFlow() {
        lifecycleScope.launchWhenStarted {

            viewModel.loginUiState.collect {
                when(it){
                    is ApiState.Success -> {
                        viewModel.isLoading.value = false
                        if (it.authResponse != null){
                            toast(this@LoginFragment.requireContext(), "Login Success")
                            startActivity(Intent(this@LoginFragment.context, MainActivity::class.java))
                        }
                    }
                    is ApiState.Error -> {
                        toast(this@LoginFragment.requireContext(), it.message)
                        viewModel.isLoading.value = false
                    }
                    is ApiState.Loading -> {
                        viewModel.isLoading.value = true
                    }
                    else -> Unit
                }
            }
        }
    }

}