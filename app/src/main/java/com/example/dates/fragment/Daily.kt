package com.example.dates.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dates.MainViewModel
import com.example.dates.MainViewModelFactory
import com.example.dates.SearchViewModel
import com.example.dates.databinding.FragmentDailyBinding
import com.example.dates.model.UserSecretary
import com.example.dates.repository.Repository
import com.example.dates.util.NetworkConnection

class Daily : Fragment() {
    private lateinit var binding: FragmentDailyBinding

    private lateinit var viewModel: MainViewModel
    private lateinit var repository: Repository
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var isConnected: NetworkConnection
    private lateinit var list: ArrayList<UserSecretary>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDailyBinding.inflate(layoutInflater)
        isConnected = NetworkConnection(requireContext())
        list = ArrayList()
        repository = Repository()
        viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        val model = ViewModelProvider(requireActivity())[SearchViewModel::class.java]


        return binding.root
    }
}