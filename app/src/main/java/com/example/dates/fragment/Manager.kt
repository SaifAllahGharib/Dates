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
import com.example.dates.adapter.AccountManagerAdapter
import com.example.dates.databinding.FragmentManagerBinding
import com.example.dates.model.UserManager
import com.example.dates.repository.Repository
import com.example.dates.util.NetworkConnection

class Manager : Fragment() {
    private lateinit var binding: FragmentManagerBinding
    private lateinit var viewModel: MainViewModel
    private var adapter: AccountManagerAdapter? = null
    private lateinit var repository: Repository
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var isConnected: NetworkConnection
    private lateinit var list: ArrayList<UserManager>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagerBinding.inflate(inflater, container, false)
        isConnected = NetworkConnection(requireContext())
        list = ArrayList()
        repository = Repository()
        viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        val model = ViewModelProvider(requireActivity())[SearchViewModel::class.java]

        if (isConnected.isInternetAvailable()) {
            binding.managerRv.visibility = View.GONE
            binding.noConnection.visibility = View.GONE

            viewModel.getAllManagers()

        } else {
            binding.managerRv.visibility = View.GONE
            binding.prog.visibility = View.GONE
            binding.noConnection.visibility = View.VISIBLE
        }

        viewModel.managersResponse.observe(context as LifecycleOwner) { response ->
            if (response.isSuccessful) {
                list = response.body()!!.managers
                binding.managerRv.visibility = View.VISIBLE
                binding.prog.visibility = View.GONE
                binding.managerRv.layoutManager = LinearLayoutManager(context)
                adapter = AccountManagerAdapter(requireContext(), requireActivity(), list)
                binding.managerRv.adapter = adapter

                if (list.isEmpty()) {
                    binding.isEmpty.visibility = View.VISIBLE
                }
            }
        }

        model.text.observe(viewLifecycleOwner) {
            val filterList = ArrayList<UserManager>()

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