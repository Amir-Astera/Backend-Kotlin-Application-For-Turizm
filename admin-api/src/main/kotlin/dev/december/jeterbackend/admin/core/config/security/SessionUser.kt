package dev.december.jeterbackend.admin.core.config.security

import dev.december.jeterbackend.shared.features.authorities.domain.models.AuthorityCode
import org.springframework.security.core.userdetails.User

class SessionUser(
  val adminId: String,
  val name: String?,
  val login: String?,
  val isEmailVerified: Boolean,
  val issuer: String,
  val picture: String,
  authorities: Collection<AuthorityCode>,

  ) : User(adminId, "", true, true, true, true, authorities)