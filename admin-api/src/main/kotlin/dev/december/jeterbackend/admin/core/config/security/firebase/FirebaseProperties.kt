package dev.december.jeterbackend.admin.core.config.security.firebase

data class FirebaseProperties(
  val sessionExpiryInDays: Int,
  val enableStrictServerSession: Boolean,
  val enableCheckSessionRevoked: Boolean,
  val enableLogoutEverywhere: Boolean,
  val apiIdentityUrl: String,
  val apiKey: String
)