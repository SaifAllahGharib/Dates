package com.example.dates

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.dates.admin.Admin
import com.example.dates.databinding.ActivityMainBinding
import com.example.dates.manager.Dates
import com.example.dates.secretary.AddDate
import com.example.dates.util.StoreToSharedPreferences

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferencesLoginManager: SharedPreferences
    private lateinit var sharedPreferencesLoginSecretary: SharedPreferences
    private lateinit var sharedPreferencesLoginAdmin: SharedPreferences
    private val time: Long = 1400L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesLoginManager =
            StoreToSharedPreferences(this, "login-status-manager").getValues()
        sharedPreferencesLoginSecretary =
            StoreToSharedPreferences(this, "login-status-secretary").getValues()
        sharedPreferencesLoginAdmin =
            StoreToSharedPreferences(this, "login-status-admin").getValues()

        if (sharedPreferencesLoginManager.getBoolean("login", false)) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(
                    Intent(
                        this,
                        Dates::class.java
                    )
                )
                finish()
            }, time)
        }

        if (sharedPreferencesLoginSecretary.getBoolean("login", false)) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(
                    Intent(
                        this,
                        AddDate::class.java
                    )
                )
                finish()
            }, time)
        }

        if (sharedPreferencesLoginAdmin.getBoolean("login", false)) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(
                    Intent(
                        this,
                        Admin::class.java
                    )
                )
                finish()
            }, time)
        }

        if (
            !sharedPreferencesLoginManager.getBoolean("login", false) &&
            !sharedPreferencesLoginSecretary.getBoolean("login", false) &&
            !sharedPreferencesLoginAdmin.getBoolean("login", false)
        ) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(
                    Intent(
                        this,
                        Welcome::class.java
                    )
                )
                finish()
            }, time)
        }
    }
}