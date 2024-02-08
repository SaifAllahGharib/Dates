package com.example.dates.model

data class DateResponse(
    val date: ArrayList<Date>,
    val error: String,
    val message: String
)