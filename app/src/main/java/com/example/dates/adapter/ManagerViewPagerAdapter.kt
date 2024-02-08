package com.example.dates.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dates.fragment.AllDates
import com.example.dates.fragment.Daily
import com.example.dates.fragment.Weekly

class ManagerViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Daily()
            1 -> Weekly()
            2 -> AllDates()
            else -> {
                Daily()
            }
        }
    }
}