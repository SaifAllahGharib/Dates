package com.example.dates.util

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class StoreToSharedPreferences(context: Context, name: String) {
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(
        name,
        AppCompatActivity.MODE_PRIVATE
    )
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun editor(): SharedPreferences.Editor {
        return editor
    }

    fun getValues(): SharedPreferences {
        return sharedPreferences
    }
}