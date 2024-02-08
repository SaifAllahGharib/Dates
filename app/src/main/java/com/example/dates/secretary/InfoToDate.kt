package com.example.dates.secretary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dates.databinding.ActivityInfoToDateBinding

class InfoToDate : AppCompatActivity() {
    private lateinit var binding: ActivityInfoToDateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoToDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            startActivity(Intent(this, AddDate::class.java))
            finish()
        }

        binding.date.text = intent.getStringExtra("date")
        binding.time.text = intent.getStringExtra("time")
        binding.person.text = intent.getStringExtra("person")
        binding.topic.text = intent.getStringExtra("topic")
        binding.inOrOut.text = intent.getStringExtra("inOrOut")
        binding.address.text = intent.getStringExtra("address")
        binding.status.text = intent.getStringExtra("completed")
    }

    override fun onBackPressed() {
        super.onBackPressed()

        startActivity(Intent(this, AddDate::class.java))
        finish()
    }
}