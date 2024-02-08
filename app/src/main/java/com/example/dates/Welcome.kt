package com.example.dates

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dates.admin.AdminLogin
import com.example.dates.databinding.ActivityWelcomeBinding
import com.example.dates.manager.ManagerLogin
import com.example.dates.secretary.SecretaryLogin

class Welcome : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdmin.setOnClickListener {
            startActivity(Intent(this, AdminLogin::class.java))
            finish()
        }

        binding.btnManager.setOnClickListener {
            startActivity(Intent(this, ManagerLogin::class.java))
            finish()
        }

        binding.btnSecretary.setOnClickListener {
            startActivity(Intent(this, SecretaryLogin::class.java))
            finish()
        }
    }
}