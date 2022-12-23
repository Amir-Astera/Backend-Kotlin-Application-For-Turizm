package dev.december.jeterbackend.client.features.clients.presentation.rest

import dev.december.jeterbackend.client.core.config.security.SessionUser
import dev.december.jeterbackend.client.features.clients.domain.usecases.*
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
@RequestMapping("/api/favorites")
@Tag(name = "favorites", description = "API for favorite suppliers")
class FavoriteController(
    private val addSupplierToFavoritesUseCase: AddSupplierToFavoritesUseCase,
    private val deleteSupplierFromFavoritesUseCase: DeleteSupplierFromFavoritesUseCase,
    private val getFavoriteSuppliersUseCase: GetFavoriteSuppliersUseCase,
) {

    @SecurityRequirement(name = "security_auth")
    @PostMapping("/{supplierId}")
    fun addSupplierToFavorites(
        @PathVariable supplierId: String,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id
        return mono { addSupplierToFavoritesUseCase(AddToFavoritesParams(userId ,supplierId)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @GetMapping()
    fun getFavoriteSuppliers(
        @Parameter(hidden = true) request: ServerHttpRequest,
        @RequestParam(required = false, defaultValue = "0")
        page: Int,
        @RequestParam(required = false, defaultValue = "10")
        size: Int,
        authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id
        return mono { getFavoriteSuppliersUseCase(GetFavoriteSuppliers(userId, page, size) )}.map {
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
    @DeleteMapping("/{supplierId}")
    fun deleteSupplierFromFavorites(
        @PathVariable supplierId: String,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id
        return mono { deleteSupplierFromFavoritesUseCase(DeleteFromFavoritesParams(userId = userId, supplier = supplierId)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure)
                }
            }
        }
    }

}
