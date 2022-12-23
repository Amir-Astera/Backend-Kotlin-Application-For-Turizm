package dev.december.jeterbackend.admin.features.authorization.domain.usecaces

import com.google.firebase.ErrorCode
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import dev.december.jeterbackend.admin.features.authorization.domain.services.FirebaseAuthService
import dev.december.jeterbackend.admin.features.authorization.presentation.dto.AuthResponseDto
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class AuthWithPhoneUseCase (
    private val service: FirebaseAuthService,
    private val firebaseAuth: FirebaseAuth
) : UseCase<AuthParams, AuthResponseDto> {
    override suspend fun invoke(params: AuthParams): Data<AuthResponseDto> {
        val credentials = service.getCredentials(params)
        val phone = credentials.first()
        val user = firebaseAuth.getUserByPhoneNumber(phone) ?: throw FirebaseAuthException(FirebaseException(ErrorCode.NOT_FOUND, "Not found supplier with this number", Throwable()))
        val email = user.email
        val password = credentials.last()

        return Data.Success(service.auth(email, password))
    }
}
