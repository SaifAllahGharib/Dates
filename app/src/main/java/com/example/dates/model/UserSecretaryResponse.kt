package com.example.dates.model

data class UserSecretaryResponse(
    val user: ArrayList<UserSecretary>,
    val error: String,
    val message: String
)