package dev.december.jeterbackend.client.features.authorization.domain.services

import dev.december.jeterbackend.client.features.authorization.domain.usecases.AuthParams
import dev.december.jeterbackend.client.features.authorization.presentation.dto.AuthResponseDto
import dev.december.jeterbackend.shared.core.domain.model.OsType
import dev.december.jeterbackend.shared.core.results.Data

interface FirebaseAuthService {
    suspend fun auth(email: String, password: String, osType: OsType): Data<AuthResponseDto>
    suspend fun getCredentials(params: AuthParams): List<String>
    suspend fun refreshToken(refreshToken: String): Data<AuthResponseDto>
    suspend fun resetPassword(
        id: String,
        newPassword: String,
        signInProvider: String?
    ): Data<Unit>
    suspend fun resetPasswordEmail(email: String): Data<Unit>
    suspend fun authCredentials(
        providerId: String,
        idToken: String,
        osType: OsType
    ): Data<AuthResponseDto>
}