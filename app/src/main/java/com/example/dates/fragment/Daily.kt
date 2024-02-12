package com.example.dates.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dates.MainViewModel
import com.example.dates.MainViewModelFactory
import com.example.dates.SearchViewModel
import com.example.dates.adapter.ManagerDatesAdapter
import com.example.dates.databinding.FragmentDailyBinding
import com.example.dates.model.Date
import com.example.dates.repository.Repository
import com.example.dates.util.NetworkConnection

class Daily : Fragment() {
    private lateinit var binding: FragmentDailyBinding
    private var adapter: ManagerDatesAdapter? = null
    private lateinit var viewModel: MainViewModel
    private lateinit var repository: Repository
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var isConnected: NetworkConnection
    private lateinit var list: ArrayList<Date>

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

        if (isConnected.isInternetAvailable()) {
            viewModel.getDailyDate()
        } else {
            binding.prog.visibility = View.GONE
            binding.dailyDatesRv.visibility = View.GONE
            binding.noConnection.visibility = View.VISIBLE
        }

        viewModel.datesResponse.observe(requireContext() as LifecycleOwner) { response ->
            if (response.isSuccessful) {
                val res = response.body()!!

                if (res.date.isNotEmpty()) {
                    binding.prog.visibility = View.GONE
                    binding.dailyDatesRv.visibility = View.VISIBLE

                    list = res.date
                    adapter = ManagerDatesAdapter(requireContext(), requireActivity(), list)
                    binding.dailyDatesRv.layoutManager = LinearLayoutManager(requireContext())
                    binding.dailyDatesRv.adapter = adapter
                } else {
                    binding.prog.visibility = View.GONE
                    binding.dailyDatesRv.visibility = View.GONE
                    binding.isEmpty.visibility = View.VISIBLE
                }
            } else {
                Toast.makeText(requireContext(), "حدث خطأ", Toast.LENGTH_SHORT).show()
            }
        }

        model.text.observe(viewLifecycleOwner) {
            val filterList = ArrayList<Date>()

            for (item in list) {
                if (item.ap_date.contains(it)) {
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