package dev.december.jeterbackend.client.features.authorization.presentation.dto

data class ResetPasswordEmailRequestDto(
    val requestType: String,
    val email: String
)
