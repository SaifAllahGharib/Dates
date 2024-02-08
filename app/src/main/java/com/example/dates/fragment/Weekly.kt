package com.example.dates.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dates.databinding.FragmentWeeklyBinding

class Weekly : Fragment() {
    private lateinit var binding: FragmentWeeklyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeeklyBinding.inflate(layoutInflater)

        return binding.root
    }
}