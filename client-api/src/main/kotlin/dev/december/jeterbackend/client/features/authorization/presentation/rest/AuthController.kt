package dev.december.jeterbackend.client.features.authorization.presentation.rest

import dev.december.jeterbackend.client.core.config.security.SessionUser
import dev.december.jeterbackend.client.features.authorization.domain.usecases.*
import dev.december.jeterbackend.shared.core.results.Data
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.Parameter
import kotlinx.coroutines.reactor.mono
import org.springframework.http.CacheControl
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@Hidden
@RestController
class AuthController(
    private val authUseCase: AuthUseCase,
    private val authWithPhoneUseCase: AuthWithPhoneUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val resetPasswordEmailUseCase: ResetPasswordEmailUseCase
) {
    @PostMapping("/authEmail")
    fun create(
        @RequestHeader("Sec-CH-UA-Platform",required = false) osType: String?,
        @Parameter(hidden = true)
        request: ServerHttpRequest
    ): Mono<ResponseEntity<Any>> {
        val authorizationHeader = request.headers.getFirst(HttpHeaders.AUTHORIZATION) ?: ""
        val encodedToken = authorizationHeader.split(' ').lastOrNull() ?: ""
        return mono { authUseCase(AuthParams(encodedToken, osType)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity
                        .status(HttpStatus.OK)
                        .cacheControl(CacheControl.noCache())
                        .body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @PostMapping("/auth")
    fun createPhone(
        @RequestHeader("Sec-CH-UA-Platform",required = false) osType: String?,
        @Parameter(hidden = true)
        request: ServerHttpRequest
    ): Mono<ResponseEntity<Any>> {
        val authorizationHeader = request.headers.getFirst(HttpHeaders.AUTHORIZATION) ?: ""
        val encodedToken = authorizationHeader.split(' ').lastOrNull() ?: ""
        println(encodedToken)
        return mono { authWithPhoneUseCase(AuthParams(encodedToken, osType)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity
                        .status(HttpStatus.OK)
                        .cacheControl(CacheControl.noCache())
                        .body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @PostMapping("/refreshToken")
    fun refreshToken(
        @Parameter(hidden = true) request: ServerHttpRequest,
        @RequestBody refreshToken: RefreshTokenParams
    ): Mono<ResponseEntity<Any>> {
        return mono {refreshTokenUseCase(RefreshTokenParams(refreshToken.refreshToken))}.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().cacheControl(CacheControl.noCache()).body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @PostMapping("/resetPassword")
    fun resetPassword(
        @Parameter(hidden = true) request: ServerHttpRequest,
        @Parameter(hidden = true) authentication: Authentication,
        @RequestBody newPassword: ResetPasswordData
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        return mono {
            resetPasswordUseCase(
                ResetPasswordParams(
                    user.id,
                    newPassword.newPassword,
                    user.signInProvider
                )
            )
        }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }

                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }

            }
        }
    }

    @PostMapping("/resetPasswordEmail")
    fun resetPasswordEmail(
        @Parameter(hidden = true) request: ServerHttpRequest,
        @RequestBody email: ResetPasswordEmailParams
    ): Mono<ResponseEntity<Any>> {
        return mono { resetPasswordEmailUseCase(ResetPasswordEmailParams(email.email))
        }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }

                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }

            }
        }
    }
}