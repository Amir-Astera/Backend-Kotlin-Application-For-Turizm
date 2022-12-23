package dev.december.jeterbackend.client.features.orders.presentation.rest

import dev.december.jeterbackend.client.core.config.security.SessionUser
import dev.december.jeterbackend.client.features.orders.presentation.dta.PayOrderData
import dev.december.jeterbackend.client.features.orders.presentation.dta.SecureOrderData
import dev.december.jeterbackend.client.features.orders.domain.usecases.PayOrderParams
import dev.december.jeterbackend.client.features.orders.domain.usecases.PayOrderUseCase
import dev.december.jeterbackend.client.features.orders.domain.usecases.PayWithSecureParams
import dev.december.jeterbackend.client.features.orders.domain.usecases.PayWithSecureUseCase
import dev.december.jeterbackend.shared.core.results.Data
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.mono
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/api/orders")
@Tag(name = "orders", description = "The Orders API")
class OrderController(
    private val createOrderUseCase: PayOrderUseCase,
    private val payWithSecureUseCase: PayWithSecureUseCase,
) {
    @SecurityRequirement(name = "security_auth")
    @PostMapping("/pay")
    fun create(
        @RequestBody data: PayOrderData,
        @Parameter(hidden = true) authentication: Authentication,
        @Parameter(hidden = true) request: ServerHttpRequest,
    ): Mono<ResponseEntity<Any>> {
        return mono { createOrderUseCase(PayOrderParams(
            (authentication.principal as SessionUser).id, data.supplierId, data.cardCryptogram, data.communicationType, request.remoteAddress!!.hostName)
        ) }.map {
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

    @SecurityRequirement(name = "security_auth")
    @PostMapping("/payWithSecure")
    fun payWith3DSecure(
        @RequestBody data: SecureOrderData,
        @Parameter(hidden = true) authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        return mono { payWithSecureUseCase(PayWithSecureParams(data.md, data.paRes, (authentication.principal as SessionUser).id)) }.map {
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