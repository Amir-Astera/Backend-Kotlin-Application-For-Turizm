package dev.december.jeterbackend.supplier.features.tutorials.presentation.rest

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.tutorials.domain.usecases.GetTutorialListParams
import dev.december.jeterbackend.supplier.features.tutorials.domain.usecases.GetTutorialListUseCase
import dev.december.jeterbackend.supplier.features.tutorials.domain.usecases.GetTutorialParams
import dev.december.jeterbackend.supplier.features.tutorials.domain.usecases.GetTutorialUseCase
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.mono
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/tutorials")
@Tag(name = "tutorials", description = "The Tutorials API")
class TutorialController(
    private val getTutorialListUseCase: GetTutorialListUseCase,
    private val getTutorialUseCase: GetTutorialUseCase,
) {

    @SecurityRequirement(name = "security_auth")
    @GetMapping("{id}")
    fun get(
        @PathVariable id: String
    ): Mono<ResponseEntity<Any>> {
        return mono { getTutorialUseCase(GetTutorialParams(id)) }.map {
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
    @GetMapping
    fun getList(
        @RequestParam authority: PlatformRole
    ): Mono<ResponseEntity<Any>> {
        return mono { getTutorialListUseCase(GetTutorialListParams(authority)) }.map {
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

