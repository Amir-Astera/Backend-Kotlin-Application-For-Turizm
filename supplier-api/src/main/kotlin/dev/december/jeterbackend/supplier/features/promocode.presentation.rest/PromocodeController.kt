package dev.december.jeterbackend.supplier.features.promocode.presentation.rest

import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.promocodes.domain.models.PromocodeDiscountType
import dev.december.jeterbackend.shared.features.promocodes.domain.models.PromocodeSortField
import dev.december.jeterbackend.shared.features.promocodes.domain.models.PromocodeStatus
import dev.december.jeterbackend.shared.features.promocodes.domain.usecases.*
import dev.december.jeterbackend.shared.features.promocodes.presentation.dto.CreatePromocodeData
import dev.december.jeterbackend.shared.features.promocodes.presentation.dto.UpdatePromocodeData
import dev.december.jeterbackend.shared.features.promocodes.presentation.dto.UpdatePromocodeStatusData
import dev.december.jeterbackend.supplier.core.config.security.SessionUser
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
@RequestMapping("/api/promocodes")
@Tag(name = "promocodes", description = "The Promocode API")
class PromocodeController(
    private val createPromocodeUseCase: CreatePromocodeUseCase,
    private val updatePromocodeUseCase: UpdatePromocodeUseCase,
    private val updatePromocodeStatusUseCase: UpdatePromocodeStatusUseCase,
    private val getPromocodeUseCase: GetPromocodeUseCase,
    private val getPromocodeListUseCase: GetPromocodeListUseCase,
    private val deletePromocodeUseCase: DeletePromocodeUseCase,
) {

    @SecurityRequirement(name = "security_auth")
    @PostMapping
    fun create(
        @RequestBody data: CreatePromocodeData,
        @Parameter(hidden = true) request: ServerHttpRequest,
        @Parameter(hidden = true) authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        val userId = (authentication.principal as SessionUser).id
        return mono { createPromocodeUseCase(data.convert(mapOf("userId" to userId))) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.created(URI.create("${request.uri}/${it.data}")).build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PutMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody data: UpdatePromocodeData,
        authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        val userId = (authentication.principal as SessionUser).id
        return mono { updatePromocodeUseCase(data.convert(mapOf("userId" to userId, "promoId" to id))) }.map {
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

    @SecurityRequirement(name = "security_auth")
    @PutMapping(value = ["/status/{id}"])
    fun updateStatus(
        @PathVariable id: String,
        @RequestBody data: UpdatePromocodeStatusData,
        authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        val userId = (authentication.principal as SessionUser).id
        return mono { updatePromocodeStatusUseCase(data.convert(mapOf("id" to id, "userId" to userId))) }.map {
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

    @SecurityRequirement(name = "security_auth")
    @GetMapping("/{id}")
    fun get(
        @PathVariable id: String,
        authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        val userId = (authentication.principal as SessionUser).id
        return mono { getPromocodeUseCase(GetPromocodeParams(id, userId)) }.map {
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
    @GetMapping
    fun getList(
        @RequestParam(required = false)
        statuses: Set<PromocodeStatus>?,
        @RequestParam(required = false)
        types: Set<PromocodeDiscountType>?,
        @RequestParam(required = false)
        searchField: String?,
        @RequestParam(required = false, defaultValue = "CREATED_AT")
        sortField: PromocodeSortField,
        @RequestParam(required = false, defaultValue = "DESC")
        sortDirection: SortDirection,
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
        authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        val userId = (authentication.principal as SessionUser).id
        return mono { getPromocodeListUseCase(GetPromocodeListParams(
            statuses, types, sortField, sortDirection, page, size,
            createdFrom, createdTo, searchField, userId = userId, supplierId = null)) }.map {
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
    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: String,
        authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        val userId = (authentication.principal as SessionUser).id
        return mono { deletePromocodeUseCase(DeletePromocodeParams(id, userId)) }.map {
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