package dev.december.jeterbackend.supplier.features.authorization.presentation.dto

data class AuthRequestDto(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean
)