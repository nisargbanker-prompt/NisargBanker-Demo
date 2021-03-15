package com.example.myapplication.ui.auth.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.ui.auth.register.data.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        //viewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        observeResponseData()
        observeErrorData()
        return binding.root
    }

    private fun observeResponseData() {
        viewModel.responseFromNetwork.observe(viewLifecycleOwner) {
            Log.e("responseFromNetwork" , viewModel.responseFromNetwork.value!!.token!!)
            Toast.makeText(this.requireContext(), viewModel.responseFromNetwork.value!!.token, Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeErrorData() {
        viewModel.errorFromNetwork.observe(
            viewLifecycleOwner
        ) { s -> viewModel.errorFromNetwork }
    }
}