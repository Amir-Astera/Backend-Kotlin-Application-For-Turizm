package dev.december.jeterbackend.admin.features.appointments.authorization.data.services

import com.google.firebase.ErrorCode
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthException
import dev.december.jeterbackend.admin.features.appointments.authorization.domain.services.FirebaseAuthService
import dev.december.jeterbackend.admin.features.appointments.authorization.data.entities.AuthFirebaseResponse
import dev.december.jeterbackend.admin.features.appointments.authorization.domain.usecaces.AuthParams
import dev.december.jeterbackend.admin.features.appointments.authorization.presentation.dto.AuthRequestDto
import dev.december.jeterbackend.admin.features.appointments.authorization.presentation.dto.AuthResponseDto
import dev.december.jeterbackend.admin.core.config.security.SecurityProperties
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitEntity
import org.springframework.web.reactive.function.client.awaitExchange
import java.util.*

@Service
class FirebaseAuthServiceImpl(
    webClientBuilder: WebClient.Builder,
    private val securityProperties: SecurityProperties
) : FirebaseAuthService {
    private val webClient = webClientBuilder.build()

    override suspend fun auth(email: String, password: String): AuthResponseDto {
        val firebaseProps = securityProperties.firebaseProps
        val baseUrl = firebaseProps.apiIdentityUrl
        val url = "${baseUrl}:signInWithPassword?key=${firebaseProps.apiKey}"

        val requestData = AuthRequestDto(
            email = email,
            password = password,
            returnSecureToken = true
        )
        val response = webClient.post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestData)
            .awaitExchange { it.awaitEntity<AuthFirebaseResponse>() }
        val responseData = response.body
        if (response.statusCode != HttpStatus.OK || responseData == null) {
            throw FirebaseAuthException(FirebaseException(ErrorCode.UNAVAILABLE, "Authorization failed!", Throwable()))
        }
        return AuthResponseDto(
            tokenType = "Bearer",
            accessToken = responseData.idToken,
            refreshToken = responseData.refreshToken,
            expiresIn = responseData.expiresIn
        )
    }

    override suspend fun getCredentials(params: AuthParams): List<String> {
        val decodedBytes = Base64.getDecoder().decode(params.encodedToken)
        val decodedToken = String(decodedBytes)
        val credentials = decodedToken.split(":")
        if (credentials.size != 2) {
            throw IllegalArgumentException("Invalid credentials!")
        }
        return credentials
    }
}