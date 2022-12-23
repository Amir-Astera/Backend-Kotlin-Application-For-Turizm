package dev.december.jeterbackend.supplier.features.authorization.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.authorization.domain.services.FirebaseAuthService
import org.springframework.stereotype.Component

@Component
class ResetPasswordUseCase(private val service: FirebaseAuthService): UseCase<ResetPasswordParams, Unit> {
    override suspend fun invoke(params: ResetPasswordParams): Data<Unit> {
        return service.resetPassword(params.id, params.newPassword, params.signInProvider)
    }
}

data class ResetPasswordParams(
    val id: String,
    val newPassword: String,
    val signInProvider: String?
)

data class ResetPasswordData(
    val newPassword: String
)