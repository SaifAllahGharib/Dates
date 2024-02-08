package com.example.dates.model

data class UserManagerResponse(
    val user: ArrayList<UserManager>,
    val error: String,
    val message: String
)