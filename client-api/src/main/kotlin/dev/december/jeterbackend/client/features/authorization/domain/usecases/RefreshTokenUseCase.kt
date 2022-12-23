package dev.december.jeterbackend.client.features.authorization.domain.usecases

import dev.december.jeterbackend.client.features.authorization.presentation.dto.AuthResponseDto
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.authorization.domain.services.FirebaseAuthService
import org.springframework.stereotype.Component

@Component
class RefreshTokenUseCase (
    private val service: FirebaseAuthService
) : UseCase<RefreshTokenParams, AuthResponseDto> {
    override suspend fun invoke(params: RefreshTokenParams): Data<AuthResponseDto> {
        return service.refreshToken(params.refreshToken)
    }
}

data class RefreshTokenParams (
    val refreshToken: String
)