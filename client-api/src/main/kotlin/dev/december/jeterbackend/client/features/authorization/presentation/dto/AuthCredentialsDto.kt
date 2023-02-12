package dev.december.jeterbackend.client.features.authorization.presentation.dto

data class AuthCredentialsDto(
    val postBody: String,
    val requestUri: String,
    val returnIdpCredential: Boolean,
    val returnSecureToken: Boolean,
)
