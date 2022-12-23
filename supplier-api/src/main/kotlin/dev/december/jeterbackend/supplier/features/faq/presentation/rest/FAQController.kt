package dev.december.jeterbackend.supplier.features.faq.presentation.rest

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.faq.domain.usecases.GetFAQListParams
import dev.december.jeterbackend.supplier.features.faq.domain.usecases.GetFAQListUseCase
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.mono
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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
        return mono{ getFAQListUseCase(GetFAQListParams(PlatformRole.SUPPLIER)) }.map {
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
}