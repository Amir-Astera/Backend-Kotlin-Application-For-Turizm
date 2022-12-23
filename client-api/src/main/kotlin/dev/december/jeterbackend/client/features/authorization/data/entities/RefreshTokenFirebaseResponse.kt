package dev.december.jeterbackend.client.features.authorization.data.entities

data class RefreshTokenFirebaseResponse (
    val expires_in: String,
    val token_type: String,
    val refresh_token: String,
    val id_token: String,
    val user_id: String,
    val project_id: String
)