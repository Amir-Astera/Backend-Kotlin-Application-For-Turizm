package dev.december.jeterbackend.admin.features.appointments.authorization.presentation.dto

data class AuthRequestDto(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean
)