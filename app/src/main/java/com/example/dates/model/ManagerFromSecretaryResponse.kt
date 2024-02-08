package com.example.dates.model


data class ManagerFromSecretaryResponse(
    val user: List<ManagerToSecretary>,
    val error: Boolean,
    val message: String,
)