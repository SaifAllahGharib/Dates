package com.example.dates.model

data class UserManager(
    val id: Int,
    val id_secretary: Int,
    val id_admin: Int,
    val name: String,
    val email: String,
    val password: String
)