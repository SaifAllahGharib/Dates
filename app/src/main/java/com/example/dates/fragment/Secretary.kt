package com.example.dates.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dates.MainViewModel
import com.example.dates.MainViewModelFactory
import com.example.dates.SearchViewModel
import com.example.dates.adapter.AccountSecretaryAdapter
import com.example.dates.databinding.FragmentSecretaryBinding
import com.example.dates.model.UserSecretary
import com.example.dates.repository.Repository
import com.example.dates.util.NetworkConnection

class Secretary : Fragment() {
    private lateinit var binding: FragmentSecretaryBinding
    private var adapter: AccountSecretaryAdapter? = null
    private lateinit var viewModel: MainViewModel
    private lateinit var repository: Repository
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var isConnected: NetworkConnection
    private lateinit var list: ArrayList<UserSecretary>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecretaryBinding.inflate(inflater, container, false)
        isConnected = NetworkConnection(requireContext())
        list = ArrayList()
        repository = Repository()
        viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        val model = ViewModelProvider(requireActivity())[SearchViewModel::class.java]

        if (isConnected.isInternetAvailable()) {
            binding.secretaryRv.visibility = View.GONE
            binding.noConnection.visibility = View.GONE

            viewModel.getAllSecretary()

        } else {
            binding.secretaryRv.visibility = View.GONE
            binding.prog.visibility = View.GONE
            binding.noConnection.visibility = View.VISIBLE
        }

        viewModel.secretaryResponse.observe(context as LifecycleOwner) { response ->
            if (response.isSuccessful) {
                list = response.body()!!.secretary
                binding.secretaryRv.visibility = View.VISIBLE
                binding.prog.visibility = View.GONE
                binding.secretaryRv.layoutManager = LinearLayoutManager(context)
                adapter = AccountSecretaryAdapter(
                    requireContext(),
                    requireActivity(),
                    list
                )
                binding.secretaryRv.adapter = adapter

                if (list.isEmpty()) {
                    binding.isEmpty.visibility = View.VISIBLE
                }
            }
        }

        model.text.observe(viewLifecycleOwner) {
            val filterList = ArrayList<UserSecretary>()

            for (item in list) {
                if (item.name.contains(it)) {
                    filterList.add(item)
                }
            }

            if (adapter != null) {
                adapter!!.filterList(filterList)
            }
        }

        return binding.root
    }
}