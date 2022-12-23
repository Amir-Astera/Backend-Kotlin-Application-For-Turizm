package dev.december.jeterbackend.client.features.authorization.presentation.dto

data class AuthRequestDto(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean
)