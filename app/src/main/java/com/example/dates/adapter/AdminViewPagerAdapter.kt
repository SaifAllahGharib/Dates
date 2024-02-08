package com.example.dates.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dates.fragment.Manager
import com.example.dates.fragment.Secretary

class AdminViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Secretary()
            1 -> Manager()
            else -> {
                Secretary()
            }
        }
    }
}