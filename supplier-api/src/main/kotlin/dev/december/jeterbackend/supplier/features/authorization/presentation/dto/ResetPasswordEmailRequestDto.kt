package dev.december.jeterbackend.supplier.features.authorization.presentation.dto

data class ResetPasswordEmailRequestDto(
    val requestType: String,
    val email: String
)
