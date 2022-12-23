package dev.december.jeterbackend.admin.features.faq.presentation.rest

import dev.bytepride.truprobackend.admin.features.faq.presentation.dto.CreateFAQData
import dev.bytepride.truprobackend.admin.features.faq.presentation.dto.UpdateFAQData
import dev.december.jeterbackend.admin.features.faq.domain.usecases.*
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.mono
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.net.URI



@RestController
@RequestMapping("/api/faq")
@Tag(name = "faq", description = "The FAQ API")
class FAQController(
    private val createFAQUseCase: CreateFAQUseCase,
    private val getFAQListUseCase: GetFAQListUseCase,
    private val deleteFAQUseCase: DeleteFAQUseCase,
    private val deleteFAQListUseCase: DeleteFAQListUseCase,
    private val updateFAQUseCase: UpdateFAQUseCase,
) {
    @SecurityRequirement(name = "security_auth")
    @PostMapping
    fun create(
        @RequestBody data: CreateFAQData,
        @Parameter(hidden = true) request: ServerHttpRequest,
    ): Mono<ResponseEntity<Any>> {
        return mono { createFAQUseCase(data.convert()) }.map {
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
        @RequestParam authority: PlatformRole
    ): Mono<ResponseEntity<Any>> {
        return mono{ getFAQListUseCase(GetFAQListParams(authority)) }.map {
            when (it){
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
    @DeleteMapping("{id}")
    fun delete(
        @PathVariable id: String
    ): Mono<ResponseEntity<Any>> {
        return mono { deleteFAQUseCase(DeleteFAQParams(id)) }.map {
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
    @DeleteMapping
    fun deleteList(
        @RequestBody data: DeleteFAQListParams
    ): Mono<ResponseEntity<Any>> {
        return mono { deleteFAQListUseCase( data.convert() ) }.map {
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
    @PutMapping("{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody data: UpdateFAQData
    ): Mono<ResponseEntity<Any>> {
        return mono { updateFAQUseCase( UpdateFAQParams(id, data.title, data.description) ) }.map {
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