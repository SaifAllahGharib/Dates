package com.example.dates

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {
    val text = MutableLiveData<String>()

    fun sendMessage(text: String) {
        this.text.value = text
    }
}