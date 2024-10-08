package dev.december.jeterbackend.supplier.features.authorization.data.services

import com.google.firebase.auth.FirebaseAuth
import dev.december.jeterbackend.supplier.features.authorization.domain.services.FirebaseAuthService
import dev.december.jeterbackend.supplier.features.authorization.domain.usecases.AuthParams
import dev.december.jeterbackend.supplier.features.authorization.presentation.dto.AuthResponseDto
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.core.config.security.SecurityProperties
import dev.december.jeterbackend.supplier.features.authorization.data.entities.RefreshTokenFirebaseResponse
import dev.december.jeterbackend.supplier.features.authorization.domain.errors.RefreshTokenFailure
import dev.december.jeterbackend.supplier.features.authorization.domain.errors.ResetPasswordEmailFailure
import dev.december.jeterbackend.supplier.features.authorization.domain.errors.ResetPasswordFailure
import dev.december.jeterbackend.supplier.features.authorization.domain.errors.SupplierAuthFailure
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
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
    private val securityProperties: SecurityProperties,
//    private val analyticsCounterService: AnalyticsCounterService,
    private val firebaseAuth: FirebaseAuth,
    private val dispatcher: CoroutineDispatcher
) : FirebaseAuthService {
    private val webClient = webClientBuilder.build()

    override suspend fun auth(email: String, password: String): Data<AuthResponseDto> {
        return try {
//            val supplier = (userRepository.findByEmail(email))?.supplier
//                ?: return Data.Error(SupplierNotFoundFailure())
//
//            if (supplier.enableStatus != AccountEnableStatus.ENABLED) {
//                return Data.Error(SupplierDisabledFailure(isDisabled = true))
//            }
//
//            val firebaseProps = securityProperties.firebaseProps
//            val baseUrl = firebaseProps.apiIdentityUrl
//            val url = "${baseUrl}:signInWithPassword?key=${firebaseProps.apiKey}"
//
//            val requestData = AuthRequestDto(
//                email = email,
//                password = password,
//                returnSecureToken = true
//            )
//            val response = webClient.post()
//                .uri(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(requestData)
//                .awaitExchange { it.awaitEntity<AuthFirebaseResponse>() }
//            val responseData = response.body
//
//            if (response.statusCode != HttpStatus.OK || responseData == null) {
//                throw FirebaseAuthException(FirebaseException(ErrorCode.UNAVAILABLE, "Authorization failed!", Throwable()))
//            }
//
//            analyticsCounterService.countLogin()
//
//            val authResponseDto = AuthResponseDto(
//                tokenType = "Bearer",
//                accessToken = responseData.idToken,
//                refreshToken = responseData.refreshToken,
//                expiresIn = responseData.expiresIn
//            )
            val temp = AuthResponseDto("", "", "", "")

            Data.Success(temp)

        } catch (e: Exception) {
            Data.Error(SupplierAuthFailure())
        }
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

    override suspend fun refreshToken(refreshToken: String): Data<AuthResponseDto> {
        return try{
            val firebaseProps = securityProperties.firebaseProps
            val refreshURL = "https://securetoken.googleapis.com/v1/token?key=${firebaseProps.apiKey}"

            val refreshPost = webClient.post()
                .uri(refreshURL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("grant_type=refresh_token&refresh_token=${refreshToken}")
                .awaitExchange { it.awaitEntity<RefreshTokenFirebaseResponse>() }

            val responseData = refreshPost.body
            if (refreshPost.statusCode != HttpStatus.OK || responseData == null) {
                return Data.Error(RefreshTokenFailure())
            }

            val response = AuthResponseDto(
                tokenType = "Bearer",
                accessToken = responseData.id_token,
                refreshToken = responseData.refresh_token,
                expiresIn = responseData.expires_in
            )
            Data.Success(response)
        } catch (e: Exception) {
            Data.Error(RefreshTokenFailure())
        }
    }

    override suspend fun resetPassword(id: String, newPassword: String, signInProvider: String?): Data<Unit> {
        return try{
            withContext(dispatcher) {
//                if(signInProvider != "phone") {
//                    return@withContext Data.Error(SupplierOtpFailure())
//                }
//
//                (userRepository.findByIdOrNull(id) ?: return@withContext Data.Error(UserNotFoundFailure())).supplier
//                    ?: return@withContext Data.Error(SupplierNotFoundFailure())
//
//                val request = UserRecord.UpdateRequest(firebaseAuth.getUser(id).uid).setPassword(newPassword)
//                firebaseAuth.updateUser(request)

                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(ResetPasswordFailure())
        }
    }

    override suspend fun resetPasswordEmail(email: String): Data<Unit> {
        return try{
//            if (!StringUtils.hasText(email) || !email.contains("@") || !email.contains(".")) {
//                return Data.Error(UserInvalidIdentityFailure())
//            }
//
//            (userRepository.findByEmail(email) ?: return Data.Error(UserNotFoundFailure())).supplier
//                ?: return Data.Error(SupplierNotFoundFailure())
//
//            val firebaseProps = securityProperties.firebaseProps
//            val url = "https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode?key=${firebaseProps.apiKey}"
//            val requestData = ResetPasswordEmailRequestDto(
//                requestType = "PASSWORD_RESET",
//                email = email
//            )
//            val response = webClient.post()
//                .uri(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(requestData)
//                .awaitExchange { it.awaitEntity<ResetPasswordEmailFirebaseResponse>() }
//
//            val responseData = response.body
//            if (response.statusCode != HttpStatus.OK || responseData == null || responseData.email != email) {
//                return Data.Error(ResetPasswordEmailFailure())
//            }
            Data.Success(Unit)
        } catch (e: Exception) {
            Data.Error(ResetPasswordEmailFailure())
        }
    }
}