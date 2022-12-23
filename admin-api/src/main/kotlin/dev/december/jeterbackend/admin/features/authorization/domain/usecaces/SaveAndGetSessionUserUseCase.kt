package dev.december.jeterbackend.admin.features.authorization.domain.usecaces

import com.google.firebase.auth.FirebaseToken
import dev.december.jeterbackend.admin.core.config.security.SessionUser
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.authorities.domain.models.AuthorityCode
import org.springframework.stereotype.Component

@Component
class SaveSessionUserUseCase(
) : UseCase<SessionParams, SessionUser> {
    override suspend fun invoke(params: SessionParams): Data<SessionUser> {
        val token = params.token
        val phone = token.claims["phone_number"] as? String
        val login = phone ?: ""
        val adminId = token.claims["adminId"] as String
        val authorityString = token.claims["adminAuthority"] as? String
        val authorities: List<AuthorityCode> = if (authorityString != null)
            listOf(AuthorityCode.valueOf(authorityString)) else emptyList()
        return Data.Success(
            SessionUser(
                adminId = adminId,
                name = token.name ?: "",
                login = login,
                isEmailVerified = token.isEmailVerified,
                issuer = token.issuer ?: "",
                picture = token.picture ?: "",
                authorities = authorities,
            )
        )
    }
}

data class SessionParams(
    val token: FirebaseToken
)