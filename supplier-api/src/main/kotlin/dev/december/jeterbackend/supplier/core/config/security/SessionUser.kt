package dev.december.jeterbackend.supplier.core.config.security

import org.springframework.security.core.userdetails.User

class SessionUser(
  val id: String,
  val name: String?,
  val login: String?,
  val isEmailVerified: Boolean,
  val issuer: String,
  val picture: String,
  val signInProvider: String?
  //authorities: Collection<GrantedAuthority>

) : User(id, "", true, true, true, true, emptyList())