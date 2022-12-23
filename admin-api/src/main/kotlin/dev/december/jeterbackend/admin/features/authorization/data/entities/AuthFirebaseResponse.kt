package dev.december.jeterbackend.admin.features.authorization.data.entities

data class AuthFirebaseResponse(
    val kind: String,
    val localId: String,
    val email: String,
    val displayName: String,
    val idToken: String,
    val registered: Boolean,
    val refreshToken: String,
    val expiresIn: String
)