package dev.december.jeterbackend.client.features.authorization.data.entities

data class AuthCredentialsFirebaseResponse(
    val federatedId: String,
    val providerId: String,
    val localId: String,
    val emailVerified: Boolean?,
    val email: String?,
    val oauthIdToken: String?,
    val oauthAccessToken: String?,
    val oauthTokenSecret: String?,
    val firstName: String?,
    val lastName: String?,
    val fullName: String?,
    val displayName: String?,
    val idToken: String,
    val photoUrl: String?,
    val refreshToken: String,
    val expiresIn: String,
    val needConfirmation: Boolean?,
    val rawUserInfo: String,
)
