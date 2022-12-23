package dev.december.jeterbackend.supplier.features.authorization.domain.usecases

import com.google.firebase.auth.FirebaseToken
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.core.config.security.SessionUser
import org.springframework.stereotype.Component

@Component
class SaveSessionUserUseCase : UseCase<SessionParams, SessionUser> {
  override suspend fun invoke(params: SessionParams): Data<SessionUser> {
    val token = params.token
    println(token)
    val phone = token.claims["phone_number"] as? String
    val login = phone ?: token.email ?: ""
    val firebase = token.claims["firebase"] as Map<*, *>
    val signInProvider = firebase["sign_in_provider"] as? String

    return Data.Success(
      SessionUser(
        id = token.uid,
        name = token.name ?: "",
        login = login,
        isEmailVerified = token.isEmailVerified,
        issuer = token.issuer ?: "",
        picture = token.picture ?: "",
        signInProvider = signInProvider
      )
    )
  }
}

data class SessionParams(
  val token: FirebaseToken
)