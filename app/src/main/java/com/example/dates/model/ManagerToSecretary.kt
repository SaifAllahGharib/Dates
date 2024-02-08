package com.example.dates.model

data class ManagerToSecretary(
    val id: Int,
    val id_manager: Int,
    val id_secretary: Int,
    val name_manager: String,
    val name_secretary: String
)