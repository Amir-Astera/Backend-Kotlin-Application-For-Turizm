package dev.december.jeterbackend.client.features.payments.presentation.rest

import dev.december.jeterbackend.client.core.config.security.SessionUser
import dev.december.jeterbackend.client.features.payments.domain.usecases.CreatePaymentUseCase
import dev.december.jeterbackend.client.features.payments.domain.usecases.GetPaymentListParams
import dev.december.jeterbackend.client.features.payments.domain.usecases.GetPaymentListUseCase
import dev.december.jeterbackend.client.features.payments.presentation.dto.CreatePaymentData
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.mono
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.net.URI
import java.time.LocalDateTime


@RestController
@RequestMapping("/api/payment")
@Tag(name = "payments", description = "The Payments API")
class PaymentController(
    private val createPaymentUseCase: CreatePaymentUseCase,
    private val getPaymentListUseCase: GetPaymentListUseCase
) {
    @SecurityRequirement(name = "security_auth")
    @PostMapping
    fun create(
        @RequestBody data: CreatePaymentData,
        @Parameter(hidden = true) request: ServerHttpRequest,
    ): Mono<ResponseEntity<Any>> {
        return mono { createPaymentUseCase(data.convert()) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.created(URI.create("${request.uri}/${it.data}")).build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @GetMapping
    fun getAll(
        @RequestParam(required = false, defaultValue = "0")
        page: Int,
        @RequestParam(required = false, defaultValue = "10")
        size: Int,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        createdFrom: LocalDateTime?,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        createdTo: LocalDateTime?,
        @Parameter(hidden = true) authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {

        return mono { getPaymentListUseCase(GetPaymentListParams(
            (authentication.principal as SessionUser).id, page, size, createdFrom, createdTo
        )) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

}