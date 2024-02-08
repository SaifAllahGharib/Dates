package com.example.dates.manager

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dates.R
import com.example.dates.Welcome
import com.example.dates.adapter.ManagerViewPagerAdapter
import com.example.dates.databinding.ActivityDatesBinding
import com.example.dates.util.CustomDialog
import com.example.dates.util.StoreToSharedPreferences
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayoutMediator

class Dates : AppCompatActivity() {
    private lateinit var binding: ActivityDatesBinding
    private lateinit var tabs: ArrayList<String>
    private lateinit var sharedPreferencesLoginAdmin: SharedPreferences.Editor
    private lateinit var dialog: CustomDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tabs = arrayListOf("اليومي", "الاسبوعي", "الكل")
        sharedPreferencesLoginAdmin =
            StoreToSharedPreferences(this, "login-status-manager").editor()
        dialog = CustomDialog(
            this,
            layoutInflater.inflate(R.layout.signout_dialog, null),
            R.style.CustomAlert
        )

        binding.vPager.adapter = ManagerViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.vPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()

        binding.ad.setOnClickListener {
            binding.search.clearFocus()
        }

        binding.signOut.setOnClickListener {
            dialog.showAlertDialog()
        }

        dialog.view.findViewById<MaterialButton>(R.id.btnOk).setOnClickListener {
            startActivity(Intent(this, Welcome::class.java))
            finish()
            sharedPreferencesLoginAdmin.remove("login").apply()
            sharedPreferencesLoginAdmin.remove("id").apply()
            sharedPreferencesLoginAdmin.remove("name").apply()
            sharedPreferencesLoginAdmin.remove("email").apply()
        }

        dialog.view.findViewById<MaterialButton>(R.id.btnNo).setOnClickListener {
            dialog.alertDialogDismiss()
        }
    }
}