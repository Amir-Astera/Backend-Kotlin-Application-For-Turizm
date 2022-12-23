package dev.december.jeterbackend.client.features.feedbacks.presentation.rest

import dev.december.jeterbackend.client.core.config.security.SessionUser
import dev.december.jeterbackend.client.features.feedbacks.domain.usecases.*
import dev.december.jeterbackend.client.features.feedbacks.presentation.dto.CreateFeedbackData
import dev.december.jeterbackend.shared.core.results.Data
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
@RequestMapping("api/feedbacks")
@Tag(name = "feedbacks", description = "The Feedbacks API")
class FeedbackController(
    private val createFeedbackUseCase: CreateFeedbackUseCase,
    private val deleteFeedbackUseCase: DeleteFeedbackUseCase,
    private val getFeedbackListUseCase: GetFeedbackListUseCase
) {

    @SecurityRequirement(name = "security_auth")
    @PostMapping
    fun create(
        @RequestBody data: CreateFeedbackData,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        val user = authentication.principal as SessionUser
        val userId = user.id
        return mono { createFeedbackUseCase(CreateFeedbackParams(userId = userId, data)) }.map {
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
    @DeleteMapping(value = ["/{id}"])
    fun delete(
        @PathVariable id: String
    ): Mono<ResponseEntity<Any>> {
        return mono { deleteFeedbackUseCase(DeleteFeedbackParams(id)) }.map {
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
    @GetMapping
    fun getList(
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication,
        @RequestParam(required = false, defaultValue = "0")
        page: Int,
        @RequestParam(required = false, defaultValue = "10")
        size: Int,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        createdFrom: LocalDateTime?,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        createdTo: LocalDateTime?
    ): Mono<ResponseEntity<Any>> {
        return mono {
            getFeedbackListUseCase(
                GetFeedbackListParams(
                    (authentication.principal as SessionUser).id,
                    page,
                    size,
                    createdFrom,
                    createdTo
                )
            )
        }.map {
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