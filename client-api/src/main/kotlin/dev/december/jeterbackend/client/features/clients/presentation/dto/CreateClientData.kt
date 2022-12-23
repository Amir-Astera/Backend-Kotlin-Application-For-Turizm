package dev.december.jeterbackend.client.features.clients.presentation.dto

data class CreateClientData(
    val email: String,
    val phone: String,
    val password: String,
    val fullName: String,
)
