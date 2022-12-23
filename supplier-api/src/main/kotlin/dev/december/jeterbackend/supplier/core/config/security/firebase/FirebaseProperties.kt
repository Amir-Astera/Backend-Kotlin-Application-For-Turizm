package dev.december.jeterbackend.supplier.core.config.security.firebase

data class FirebaseProperties(
  val sessionExpiryInDays: Int,
  val enableStrictServerSession: Boolean,
  val enableCheckSessionRevoked: Boolean,
  val enableLogoutEverywhere: Boolean,
  val apiIdentityUrl: String,
  val apiKey: String
)