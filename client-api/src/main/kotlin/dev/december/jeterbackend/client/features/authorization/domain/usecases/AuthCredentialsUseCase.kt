package dev.december.jeterbackend.client.features.authorization.domain.usecases

import dev.december.jeterbackend.client.features.authorization.domain.services.FirebaseAuthService
import dev.december.jeterbackend.client.features.authorization.presentation.dto.AuthResponseDto
import dev.december.jeterbackend.shared.core.domain.model.OsType
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class AuthCredentialsUseCase (
    private val service: FirebaseAuthService
        ) : UseCase<AuthParams, AuthResponseDto> {
    override suspend fun invoke(params: AuthParams): Data<AuthResponseDto> {
        val credentials = service.getCredentials(params)
        val providerId = credentials.first()
        val idToken = credentials.last()
        val osType = OsType.UNKNOWN.get(params.osType)

        return service.authCredentials(
            providerId,
            idToken,
            osType
        )
    }
}