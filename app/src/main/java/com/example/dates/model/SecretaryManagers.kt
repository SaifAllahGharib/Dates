package com.example.dates.model


data class SecretaryManagers(
    val manager: List<Manager>,
    val error: Boolean,
    val message: String
)