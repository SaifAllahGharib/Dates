package com.example.dates.model

data class Date(
    val id: Int,
    val ap_date: String,
    val ap_time: String,
    val personOrSide: String,
    val topic: String,
    val insideOrOutside: String,
    val address: String,
    val completed: String,
    val note: String,
    val id_manager: Int,
    val id_secretary: Int
)
