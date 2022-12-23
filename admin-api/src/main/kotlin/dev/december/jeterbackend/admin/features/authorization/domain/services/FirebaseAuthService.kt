package dev.december.jeterbackend.admin.features.authorization.domain.services

import dev.december.jeterbackend.admin.features.authorization.domain.usecaces.AuthParams
import dev.december.jeterbackend.admin.features.authorization.presentation.dto.AuthResponseDto

interface FirebaseAuthService {
    suspend fun auth(email: String, password: String): AuthResponseDto
    suspend fun getCredentials(params: AuthParams): List<String>
}