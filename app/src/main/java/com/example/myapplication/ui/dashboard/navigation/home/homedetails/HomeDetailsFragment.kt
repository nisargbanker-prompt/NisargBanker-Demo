package com.example.myapplication.ui.dashboard.navigation.home.homedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHomeDetailsBinding
import com.example.myapplication.ui.dashboard.navigation.home.homedetails.data.HomeDetailsViewModel
import com.example.myapplication.util.getProgressDrawable
import com.example.myapplication.util.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDetailsFragment : Fragment() {

    private val viewModel: HomeDetailsViewModel by viewModels()
    private lateinit var binding: FragmentHomeDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home_details, container, false)
        viewModel.userId = arguments?.getString("userId")!!
        viewModel.getUserData()
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        observeResponseData()
        observeErrorData()
        return binding.root
    }

    private fun observeResponseData() {
        viewModel.responseFromNetwork.observe(viewLifecycleOwner) {
            it.data?.let {
                binding.user = it
                binding.imgUser.loadImage(
                    it.avatar,
                    getProgressDrawable(binding.imgUser.context)
                )
            }
        }
    }

    private fun observeErrorData() {
        viewModel.errorFromNetwork.observe(
            viewLifecycleOwner
        ) { s -> viewModel.errorFromNetwork }
    }

}