package dev.bytepride.truprobackend.client.features.suppliers.presentation.rest

import dev.december.jeterbackend.client.core.config.security.SessionUser
import dev.december.jeterbackend.client.features.appointments.domain.usecases.GetAppointmentListByClientAndSupplierParams
import dev.december.jeterbackend.client.features.appointments.domain.usecases.GetAppointmentListByClientAndSupplierUseCase
import dev.december.jeterbackend.client.features.feedbacks.domain.usecases.GetFeedbackListBySupplierParams
import dev.december.jeterbackend.client.features.feedbacks.domain.usecases.GetFeedbackListBySupplierUseCase
import dev.december.jeterbackend.client.features.suppliers.domain.usecases.*
import dev.december.jeterbackend.shared.core.domain.model.Gender
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.suppliers.domain.models.enums.SupplierAgeRange
import dev.december.jeterbackend.shared.features.suppliers.domain.models.enums.SupplierExperienceRange
import dev.december.jeterbackend.shared.features.suppliers.domain.models.enums.SupplierPriceRange
import dev.december.jeterbackend.shared.features.suppliers.domain.models.enums.SupplierRatingRange
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
import java.time.LocalDate


@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "suppliers", description = "The Suppliers API")
class SupplierController(
    private val getSupplierListUseCase: GetSupplierListUseCase,
    private val getSupplierByIdUseCase: GetSupplierByIdUseCase,
    private val getAppointmentListByClientAndSupplierUseCase: GetAppointmentListByClientAndSupplierUseCase,
    private val getFeedbackListBySupplierUseCase: GetFeedbackListBySupplierUseCase,
    private val getSupplierFreeTimeUseCase: GetSupplierFreeTimeUseCase,
    private val getSupplierCalendarUseCase: GetSupplierCalendarUseCase,
) {

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
        searchField: String?,
        @RequestParam(required = false)
        ratingFilter: SupplierRatingRange?,
        @RequestParam(required = false)
        experienceFilter: SupplierExperienceRange?,
        @RequestParam(required = false)
        ageFilter: SupplierAgeRange?,
        @RequestParam(required = false)
        genderFilter: Gender?,
        @RequestParam(required = false)
        priceFilter: SupplierPriceRange?,
    ): Mono<ResponseEntity<Any>> {
        return mono {
            getSupplierListUseCase(
                GetSupplierListParams(
                    (authentication.principal as SessionUser).id,
                    page,
                    size,
                    searchField,
                    ratingFilter,
                    experienceFilter,
                    ageFilter,
                    genderFilter,
                    priceFilter,
                )
            )
        }.map {
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
    @GetMapping("/{id}")
    fun get(
        @Parameter(hidden = true) authentication: Authentication,
        @PathVariable id : String,
    ): Mono<ResponseEntity<Any>> {
        return mono {
            getSupplierByIdUseCase(GetSupplierByIdParams(
                (authentication.principal as SessionUser).id,
                id))
        }.map {
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
    @GetMapping("/{id}/appointments")
    fun getAppointmentList(
        @Parameter(hidden = true) authentication: Authentication,
        @PathVariable id: String,
    ): Mono<ResponseEntity<Any>> {
        return mono {
            getAppointmentListByClientAndSupplierUseCase(
                GetAppointmentListByClientAndSupplierParams(
                    (authentication.principal as SessionUser).id,
                    id
                )
            )
        }.map {
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
    @GetMapping("/{id}/feedbacks")
    fun getFeedbackList(
        @PathVariable id: String,
        @RequestParam(required = false, defaultValue = "0")
        page: Int,
        @RequestParam(required = false, defaultValue = "10")
        size: Int,
    ): Mono<ResponseEntity<Any>> {
        return mono {
            getFeedbackListBySupplierUseCase(
                GetFeedbackListBySupplierParams(
                    id,
                    page,
                    size,
                )
            )
        }.map {
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
    @GetMapping("/{id}/calendar")
    fun getCalendar(
        @PathVariable id: String,
        @RequestParam
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        firstDayOfMonth: LocalDate
    ): Mono<ResponseEntity<Any>> {
        return mono { getSupplierCalendarUseCase(
            GetSupplierCalendarParams(id, firstDayOfMonth)
        ) }.map {
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
    @GetMapping("/{id}/get-free-hours")
    fun getFreeTime(
        @PathVariable("id") supplierId: String,
        @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate
    ): Mono<ResponseEntity<Any>> {
        return mono { getSupplierFreeTimeUseCase(
            GetSupplierFreeTimeParams(supplierId, date)
        ) }.map {
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