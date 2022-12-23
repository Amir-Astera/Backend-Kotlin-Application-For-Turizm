package dev.december.jeterbackend.supplier.features.authorization.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.authorization.domain.services.FirebaseAuthService
import org.springframework.stereotype.Component

@Component
class ResetPasswordEmailUseCase (
    private val service: FirebaseAuthService
    ) : UseCase<ResetPasswordEmailParams, Unit> {
    override suspend fun invoke(params: ResetPasswordEmailParams): Data<Unit> {
        return service.resetPasswordEmail(params.email)
    }
}

data class ResetPasswordEmailParams(
    val email: String
)
