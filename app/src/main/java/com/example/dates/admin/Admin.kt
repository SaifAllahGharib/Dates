package com.example.dates.admin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.dates.R
import com.example.dates.SearchViewModel
import com.example.dates.Welcome
import com.example.dates.adapter.AdminViewPagerAdapter
import com.example.dates.databinding.ActivityAdminBinding
import com.example.dates.util.CustomDialog
import com.example.dates.util.StoreToSharedPreferences
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayoutMediator

class Admin : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private lateinit var tabs: ArrayList<String>
    private lateinit var sharedPreferencesLoginAdmin: SharedPreferences.Editor
    private lateinit var dialog: CustomDialog
    private lateinit var model: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this)[SearchViewModel::class.java]
        tabs = arrayListOf("مساعد", "مدير")
        sharedPreferencesLoginAdmin =
            StoreToSharedPreferences(this, "login-status-admin").editor()
        dialog = CustomDialog(
            this,
            layoutInflater.inflate(R.layout.signout_dialog, null),
            R.style.CustomAlert
        )

        binding.vPager.adapter = AdminViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.vPager) { tab, position ->
            tab.text = tabs[position]
        }.attach()

        binding.addAccount.setOnClickListener {
            startActivity(Intent(this, CreateAccounts::class.java))
            finish()
        }

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                model.sendMessage(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

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