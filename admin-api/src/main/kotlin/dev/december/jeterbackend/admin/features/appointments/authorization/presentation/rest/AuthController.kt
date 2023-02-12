package dev.december.jeterbackend.admin.features.appointments.authorization.presentation.rest

import dev.december.jeterbackend.admin.features.appointments.authorization.domain.usecaces.AuthWithPhoneUseCase
import dev.december.jeterbackend.admin.features.appointments.authorization.domain.usecaces.AuthParams
import dev.december.jeterbackend.admin.features.appointments.authorization.domain.usecaces.AuthUseCase
import dev.december.jeterbackend.shared.core.results.Data
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.Parameter
import kotlinx.coroutines.reactor.mono
import org.springframework.http.CacheControl
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@Hidden
@RestController
class AuthController(
    private val authUseCase: AuthUseCase,
    private val authWithPhoneUseCase: AuthWithPhoneUseCase
) {
    @PostMapping("/auth")
    fun create(
        @Parameter(hidden = true)
        request: ServerHttpRequest
    ): Mono<ResponseEntity<Any>> {
        val authorizationHeader = request.headers.getFirst(HttpHeaders.AUTHORIZATION) ?: ""
        val encodedToken = authorizationHeader.split(' ').lastOrNull() ?: ""
        return mono { authUseCase(AuthParams(encodedToken)) }.map {
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

    @PostMapping("/authPhone")
    fun createPhone(
        @Parameter(hidden = true)
        request: ServerHttpRequest
    ): Mono<ResponseEntity<Any>> {
        val authorizationHeader = request.headers.getFirst(HttpHeaders.AUTHORIZATION) ?: ""
        val encodedToken = authorizationHeader.split(' ').lastOrNull() ?: ""
        return mono { authWithPhoneUseCase(AuthParams(encodedToken)) }.map {
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
}