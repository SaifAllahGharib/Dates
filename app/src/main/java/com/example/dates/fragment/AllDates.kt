package com.example.dates.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dates.databinding.FragmentAllDatesBinding

class AllDates : Fragment() {
    private lateinit var binding: FragmentAllDatesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllDatesBinding.inflate(layoutInflater)

        return binding.root
    }
}