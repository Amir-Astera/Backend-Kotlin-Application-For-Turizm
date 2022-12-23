package dev.december.jeterbackend.admin.features.articles.presentation.rest

import dev.december.jeterbackend.admin.features.articles.domain.usecases.*
import dev.december.jeterbackend.admin.features.articles.presentation.dto.*
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.articles.domain.models.ArticleSortDirection
import dev.december.jeterbackend.shared.features.articles.domain.models.ArticleSortField
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
@RequestMapping("/api/articles")
@Tag(name = "articles", description = "The Articles API")
class ArticleController(
    private val createArticleUseCase: CreateArticleUseCase,
    private val getArticleUseCase: GetArticleUseCase,
    private val getArticleListUseCase: GetArticleListUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val deleteArticleListUseCase: DeleteArticleListUseCase,
    private val updateArticleUseCase: UpdateArticleUseCase,
    private val addSuppliersToStoriesUseCase: AddSuppliersToStoriesUseCase,
    private val deleteSuppliersFromStoriesUseCase: DeleteSuppliersFromStoriesUseCase,
    private val getStoriesByIdUseCase: GetStoriesByIdUseCase,
    private val patchStoriesUseCase: PatchStoriesUseCase,
) {
    @SecurityRequirement(name = "security_auth")
    @PostMapping
    fun create(
        @RequestBody data: CreateArticleData,
        @Parameter(hidden = true) request: ServerHttpRequest,
    ): Mono<ResponseEntity<Any>> {
        return mono { createArticleUseCase(data.convert()) }.map {
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
        @RequestParam(required = false, defaultValue = "PRIORITY")
        sortField: ArticleSortField,
        @RequestParam(required = false, defaultValue = "ASC")
        sortDirection: ArticleSortDirection,
        @RequestParam(required = false, defaultValue = "0")
        page: Int,
        @RequestParam(required = true, defaultValue = "10")
        size: Int,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        createdFrom: LocalDateTime?,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        createdTo: LocalDateTime?
    ): Mono<ResponseEntity<Any>>{
        return mono{
            getArticleListUseCase(
                GetArticleListParams(
                    sortField,
                    sortDirection,
                    page,
                    size,
                    createdFrom,
                    createdTo
                )
            )
        }.map {
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
    @GetMapping("{id}")
    fun get(
        @PathVariable id : String
    ) : Mono<ResponseEntity<Any>>{
        return mono { getArticleUseCase(GetArticleParams(id)) }.map {
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
    @DeleteMapping("{id}")
    fun delete(
        @PathVariable id: String
    ): Mono<ResponseEntity<Any>> {
        return mono { deleteArticleUseCase(DeleteArticleParams(id)) }.map {
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
        @RequestBody data: DeleteArticleListData
    ): Mono<ResponseEntity<Any>> {
        return mono {
            deleteArticleListUseCase(data.convert() )
        }.map {
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
        @RequestBody data: UpdateArticleData
    ): Mono<ResponseEntity<Any>> {
        return mono { updateArticleUseCase(data.convert(mapOf("id" to id))) }.map {
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
    @PostMapping("stories/{id}/suppliers")
    fun addSuppliersToStories(
        @PathVariable id: String,
        @RequestBody data: AddSuppliersData
    ): Mono<ResponseEntity<Any>> {
        return mono {
            addSuppliersToStoriesUseCase(
                AddSuppliersStoriesParams(
                    id,
                    data.supplierIds
                )
            )
        }.map {
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
    @DeleteMapping("stories/{id}/suppliers")
    fun deleteSuppliersFromStories(
        @PathVariable id: String,
        @RequestBody data: AddSuppliersData
    ): Mono<ResponseEntity<Any>> {
        return mono {
            deleteSuppliersFromStoriesUseCase(
                DeleteSuppliersStoriesParams(
                    id,
                    data.supplierIds
                )
            )
        }.map {
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
    @GetMapping("stories/{id}")
    fun getStoriesById(
        @PathVariable id: String,
    ): Mono<ResponseEntity<Any>> {
        return mono {
            getStoriesByIdUseCase(
                GetStoriesByIdParams(
                    id,
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
    @PatchMapping("{id}/stories")
    fun patchStories(
        @PathVariable id: String,
        @RequestBody data: PatchStoriesData
    ): Mono<ResponseEntity<Any>>  {
        return mono {
            patchStoriesUseCase(
                PatchStoriesParams(
                    id,
                    data.stories
                )
            )
        }.map {
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