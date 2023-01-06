package dev.december.jeterbackend.supplier.features.authorization.domain.usecases

import dev.december.jeterbackend.shared.core.domain.model.OsType
import dev.december.jeterbackend.supplier.features.authorization.domain.services.FirebaseAuthService
import dev.december.jeterbackend.supplier.features.authorization.presentation.dto.AuthResponseDto
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class AuthUseCase(
    private val service: FirebaseAuthService
) : UseCase<AuthParams, AuthResponseDto> {
    override suspend fun invoke(params: AuthParams): Data<AuthResponseDto> {
        val credentials = service.getCredentials(params)
        val email = credentials.first()
        val password = credentials.last()
        val osType = OsType.UNKNOWN.get(params.osType)

        return service.auth(email, password, osType)
    }
}

data class AuthParams(
    val encodedToken: String,
    val osType: String?,
)