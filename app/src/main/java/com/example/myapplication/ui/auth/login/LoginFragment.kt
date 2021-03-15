package com.example.myapplication.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.ui.auth.login.data.LoginViewModel
import com.example.myapplication.ui.dashboard.MainActivity
import dagger.hilt.android.AndroidEntryPoint

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
        observeResponseData()
        observeErrorData()
        return binding.root
    }

    private fun observeResponseData() {
        viewModel.responseFromNetwork.observe(viewLifecycleOwner) {
            if (viewModel.responseFromNetwork.value != null){
                startActivity(Intent(this.requireContext(), MainActivity::class.java))
                viewModel.responseFromNetwork.value = null
            }
            //this.requireActivity().toast(viewModel.responseFromNetwork.value!!.token!!)
        }
    }

    private fun observeErrorData() {
        viewModel.errorFromNetwork.observe(
            viewLifecycleOwner
        ) { s -> viewModel.errorFromNetwork }
    }
}