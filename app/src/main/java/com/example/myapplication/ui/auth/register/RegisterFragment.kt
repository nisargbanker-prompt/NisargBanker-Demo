package com.example.myapplication.ui.auth.register

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
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.ui.auth.register.data.RegisterViewModel
import com.example.myapplication.ui.dashboard.MainActivity
import com.example.myapplication.util.ApiState
import com.example.myapplication.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        observeStateFlow()
        return binding.root
    }

    private fun observeStateFlow() {
        lifecycleScope.launchWhenStarted {
            viewModel.registerUiState.collect {
                when(it){
                    is ApiState.Success -> {
                        viewModel.isLoading.value = false
                        if (it.authResponse != null){
                            toast(this@RegisterFragment.requireContext(), "Register Success")
                            startActivity(Intent(this@RegisterFragment.context, MainActivity::class.java))
                        }
                    }
                    is ApiState.Error -> {
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