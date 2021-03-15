package com.example.myapplication.ui.dashboard.navigation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.callback.RecyclerViewClickListener
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.dashboard.navigation.home.data.DataItem
import com.example.myapplication.ui.dashboard.navigation.home.data.HomeUserAdapter
import com.example.myapplication.ui.dashboard.navigation.home.data.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),  RecyclerViewClickListener {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    private val listAdapter = HomeUserAdapter(arrayListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        observeResponseData()
        observeErrorData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recUserList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
        }
    }

    private fun observeResponseData() {
        viewModel.responseFromNetwork.observe(viewLifecycleOwner) {
            it.data?.let {
                listAdapter.updateUserList(it as List<DataItem>)
            }
        }
    }

    private fun observeErrorData() {
        viewModel.errorFromNetwork.observe(
            viewLifecycleOwner
        ) { s -> viewModel.errorFromNetwork }
    }

    override fun onRecyclerViewItemClick(view: View, userId: String) {
        val bundle = Bundle()
        bundle.putString("userId", userId)
        Navigation.findNavController(view).navigate(
            R.id.action_nav_home_to_homeDetailsFragment,
            bundle
        )
    }
}