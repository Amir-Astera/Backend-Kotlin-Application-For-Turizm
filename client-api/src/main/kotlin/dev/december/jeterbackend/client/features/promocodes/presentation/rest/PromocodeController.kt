package dev.december.jeterbackend.client.features.promocodes.presentation.rest

import dev.december.jeterbackend.client.core.config.security.SessionUser
import dev.december.jeterbackend.client.features.promocodes.domain.usecases.*
import dev.december.jeterbackend.shared.core.results.Data
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.mono
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/promocodes")
@Tag(name = "promocodes", description = "The Promocode API")
class PromocodeController(
    private val getDiscountUseCase: GetDisocuntUseCase,
    private val getFinalPriceUseCase: GetFinalPriceUseCase,
    private val applyPromocodeUseCase: ApplyPromocodeUseCase,
    ) {

    @SecurityRequirement(name = "security_auth")
    @GetMapping("/discount")
    fun getDiscount(
        @RequestParam code: String,
        @RequestParam supplierId: String,
        authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        val userId = (authentication.principal as SessionUser).id
        return mono { getDiscountUseCase(GetDiscountParams(code, supplierId, userId)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @GetMapping("/discount/finalPrice")
    fun finalPrice(
        @RequestParam code: String,
        @RequestParam supplierId: String,
        @RequestParam price: Double,
        authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        val userId = (authentication.principal as SessionUser).id
        return mono { getFinalPriceUseCase(GetFinalPriceParams(code, supplierId, userId, price)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PostMapping("/{id}/apply")
    fun applyDiscount(
        @PathVariable id: String,
        authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        val userId = (authentication.principal as SessionUser).id
        return mono { applyPromocodeUseCase(ApplyPromocodeParams(id, userId)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().body(it.data)
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }
}