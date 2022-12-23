package dev.december.jeterbackend.admin.features.analytics.presentation.rest

import dev.december.jeterbackend.admin.features.analytics.domain.usecases.*
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.analytics.domain.models.AnalyticsCounterSortDirection
import dev.december.jeterbackend.shared.features.analytics.domain.models.AnalyticsCounterSortField
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.mono
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/analytics")
@Tag(name = "analytics", description = "The Analytics API")
class AnalyticsCounterController(
    private val getTodayAnalyticsCounterUseCase: GetTodayAnalyticsCounterUseCase,
    private val getAnalyticsCounterListUseCase: GetAnalyticsCounterListUseCase,
    private val getByDateAnalyticsCounterUseCase: GetByDateAnalyticsCounterUseCase,
) {

    @SecurityRequirement(name = "security_auth")
    @GetMapping("today")
    fun get(
    ) : Mono<ResponseEntity<Any>>{
        return mono { getTodayAnalyticsCounterUseCase(Unit) }.map {
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
    fun getAll(
        @RequestParam(required = false, defaultValue = "CREATED_AT")
        sortField: AnalyticsCounterSortField,
        @RequestParam(required = false, defaultValue = "ASC")
        sortDirection: AnalyticsCounterSortDirection,
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
            getAnalyticsCounterListUseCase(
                GetAnalyticsCounterListParams(
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
    @GetMapping("byDate")
    fun getByDate(
        @RequestParam(required = true)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        date: LocalDateTime,
    ) : Mono<ResponseEntity<Any>>{
        return mono { getByDateAnalyticsCounterUseCase(GetByDateAnalyticsCounterListParams(date)) }.map {
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