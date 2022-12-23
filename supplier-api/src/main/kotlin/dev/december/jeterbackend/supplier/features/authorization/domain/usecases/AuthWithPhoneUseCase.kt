package dev.december.jeterbackend.supplier.features.authorization.domain.usecases

import com.google.firebase.auth.FirebaseAuth
import dev.december.jeterbackend.supplier.features.authorization.presentation.dto.AuthResponseDto
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.authorization.domain.errors.RefreshTokenFailure
import dev.december.jeterbackend.supplier.features.authorization.domain.services.FirebaseAuthService
import org.springframework.stereotype.Component

@Component
class AuthWithPhoneUseCase (
    private val service: FirebaseAuthService,
    private val firebaseAuth: FirebaseAuth
) : UseCase<AuthParams, AuthResponseDto> {
    override suspend fun invoke(params: AuthParams): Data<AuthResponseDto> {
        return try {
            val credentials = service.getCredentials(params)
            val phone = credentials.first()
            val user = firebaseAuth.getUserByPhoneNumber(phone) //?: return Data.Error(SupplierNotFoundInFirebasePhoneFailure())
            val email = user.email
            val password = credentials.last()

            return service.auth(email, password)
        } catch (e:Exception) {
            Data.Error(RefreshTokenFailure())//SupplierNotFoundInFirebasePhoneFailure()
        }
    }
}