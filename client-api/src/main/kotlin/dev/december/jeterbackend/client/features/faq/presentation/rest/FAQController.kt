package dev.december.jeterbackend.client.features.faq.presentation.rest

import dev.december.jeterbackend.client.features.faq.domain.usecases.GetFAQListParams
import dev.december.jeterbackend.client.features.faq.domain.usecases.GetFAQListUseCase
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.mono
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/faq")
@Tag(name = "faq", description = "The FAQ API")
class FAQController(
    private val getFAQListUseCase: GetFAQListUseCase,
) {

    @SecurityRequirement(name = "security_auth")
    @GetMapping
    fun getAll(): Mono<ResponseEntity<Any>> {
        return mono { getFAQListUseCase(GetFAQListParams(PlatformRole.SUPPLIER)) }.map {
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