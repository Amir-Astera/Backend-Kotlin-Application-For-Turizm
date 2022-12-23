package dev.december.jeterbackend.admin.features.feedbacks.presentation.rest

import dev.december.jeterbackend.admin.features.feedbacks.presentation.dto.CreateFeedbackData
import dev.december.jeterbackend.admin.features.feedbacks.presentation.dto.DeleteFeedbackListData
import dev.december.jeterbackend.admin.features.feedbacks.presentation.dto.DisapproveFeedbackListData
import dev.december.jeterbackend.admin.features.feedbacks.domain.usecases.*
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.FeedbackSortField
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.FeedbackStatus
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.mono
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.net.URI
import java.time.LocalDateTime

@RestController
@RequestMapping("api/feedbacks")
@Tag(name = "feedbacks", description = "The Feedbacks API")
class FeedbackController(
    private val getFeedbackListUseCase: GetFeedbackListUseCase,
    private val approveFeedbackUseCase: ApproveFeedbackUseCase,
    private val disapproveFeedbackUseCase: DisapproveFeedbackUseCase,
    private val createFeedbackUseCase: CreateFeedbackUseCase,
    private val disapproveFeedbackListUseCase: DisapproveFeedbackListUseCase,
    private val undefineFeedbackUseCase: UndefineFeedbackUseCase,
    private val deleteFeedbackUseCase: DeleteFeedbackUseCase,
    private val deleteFeedbackListUseCase: DeleteFeedbackListUseCase,
) {

    @SecurityRequirement(name = "security_auth")
    @PostMapping
    fun create(
        @RequestBody data: CreateFeedbackData,
        @Parameter(hidden = true) request: ServerHttpRequest,
    ): Mono<ResponseEntity<Any>> {
        return mono { createFeedbackUseCase(data.convert()) }.map {
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
    fun getList(
        @RequestParam(required = false, defaultValue = "CREATED_AT")
        sortField: FeedbackSortField,
        @RequestParam(required = false, defaultValue = "ASC")
        sortDirection: SortDirection,
        @RequestParam(required = false, defaultValue = "0")
        page: Int,
        @RequestParam(required = false, defaultValue = "10")
        size: Int,
        @RequestParam
        statuses: Set<FeedbackStatus>,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        createdFrom: LocalDateTime?,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        createdTo: LocalDateTime?,
        @RequestParam(required = false)
        grades: Set<Float>?,
    ): Mono<ResponseEntity<Any>> {
        return mono {
            getFeedbackListUseCase(
                GetFeedbackListParams(
                    sortField,
                    sortDirection,
                    page,
                    size,
                    statuses,
                    createdFrom,
                    createdTo,
                    grades,
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

    @SecurityRequirement(name = "security_auth")
    @PutMapping(value = ["/approve/{id}"])
    fun approve(
        @PathVariable id: String
    ): Mono<ResponseEntity<Any>> {
        return mono { approveFeedbackUseCase(ApproveFeedbackParams(id)) }.map {
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
    @PutMapping(value = ["/disapprove/{id}"])
    fun disapprove(
        @PathVariable id: String
    ): Mono<ResponseEntity<Any>> {
        return mono { disapproveFeedbackUseCase(DisapproveFeedbackParams(id)) }.map {
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
    @PutMapping(value = ["/undefine/{id}"])
    fun undefine(
        @PathVariable id: String
    ): Mono<ResponseEntity<Any>> {
        return mono { undefineFeedbackUseCase(UndefineFeedbackParams(id)) }.map {
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
    @PatchMapping(value = ["/disapprove"])
    fun disapproveList(
        @RequestBody data: DisapproveFeedbackListData
    ): Mono<ResponseEntity<Any>> {
        return mono { disapproveFeedbackListUseCase(data.convert()) }.map {
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
        @RequestBody data: DeleteFeedbackListData
    ): Mono<ResponseEntity<Any>> {
        return mono { deleteFeedbackListUseCase(data.convert()) }.map {
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