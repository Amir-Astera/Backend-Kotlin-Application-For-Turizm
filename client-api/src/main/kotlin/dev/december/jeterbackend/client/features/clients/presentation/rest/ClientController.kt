package dev.december.jeterbackend.client.features.clients.presentation.rest

import dev.december.jeterbackend.client.core.config.security.SessionUser
import dev.december.jeterbackend.client.features.clients.presentation.dto.CreateClientData
import dev.december.jeterbackend.client.features.clients.presentation.dto.UpdateClientData
import dev.december.jeterbackend.client.features.clients.domain.usecases.*
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.mono
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.net.URI

@RestController
@RequestMapping("/api/clients")
@Tag(name = "clients", description = "The Client API")
class ClientController(
    private val createClientUseCase: CreateClientUseCase,
    private val getClientUseCase: GetClientUseCase,
    private val patchClientUseCase: PatchClientUseCase,
    private val deleteClientByUserIdUseCase: DeleteClientByUserIdUseCase,
    private val getClientByPhoneNumberUseCase: GetClientByPhoneNumberUseCase,
    private val updateRegistrationTokenUseCase: UpdateRegistrationTokenUseCase,
    private val restoreClientUseCase: RestoreClientUseCase
) {

    @PostMapping
    fun create(
        @RequestBody client: CreateClientData,
        @Parameter(hidden = true) request: ServerHttpRequest,
        ): Mono<ResponseEntity<Any>>{

        return mono { createClientUseCase(client.convert()) } .map {
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
    @GetMapping("")
    fun get(
        authentication: Authentication
    ) : Mono<ResponseEntity<Any>>{
        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { getClientUseCase(GetClientParams(userId)) }.map {
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
    @PatchMapping("/")
    fun update(
        @RequestBody updateClientData: UpdateClientData,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication
    ): Mono<ResponseEntity<Any>>{
        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { patchClientUseCase(PatchClientParams(userId, updateClientData)) } .map {
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
    @DeleteMapping("")
    fun deleteCurrent(
        @Parameter(hidden = true) request: ServerHttpRequest,
        @Parameter(hidden = true) authentication: Authentication
    ) : Mono<ResponseEntity<Any>>{
        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { deleteClientByUserIdUseCase(DeleteClientByUserIdParams(userId, user.signInProvider)) }.map {
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

    @GetMapping("/checkExistence")
    fun getByPhoneNumber(
        @RequestParam(required = false)
        phone: String,
    ) : Mono<ResponseEntity<Any>>{

        return mono { getClientByPhoneNumberUseCase(GetClientByPhoneParams(phone)) }.map {
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
    @PutMapping("/registration-token")
    fun updateRegistrationToken(
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
        @RequestParam registrationToken: String
    ): Mono<ResponseEntity<Any>> {

        val user = authentication.principal as SessionUser
        val userId = user.id

        return mono { updateRegistrationTokenUseCase(
            UpdateRegistrationTokenParams(
                userId, registrationToken
            )
        ) }.map {
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

    @PostMapping("/restore")
    fun restore(
        @Parameter(hidden = true) request: ServerHttpRequest,
        @Parameter(hidden = true) authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        return mono { restoreClientUseCase(RestoreClientParams(user.id, user.signInProvider)) }.map {
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
