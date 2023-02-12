package dev.december.jeterbackend.supplier.features.tour.presentation.rest

import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentStatus
import dev.december.jeterbackend.shared.features.tours.domain.models.TourStatus
import dev.december.jeterbackend.supplier.core.config.security.SessionUser
import dev.december.jeterbackend.supplier.features.tour.domain.usecases.*
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
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/tour")
@Tag(name = "tours", description = "The Tours API")
class TourController(
    private val getTourListUseCase: GetTourListUseCase,
    private val deleteTourUseCase: DeleteTourUseCase,
//    private val confirmTourUseCase: ConfirmTourUseCase,
//    private val completeTourUseCase: CompleteTourUseCase,
//    private val cancelTourUseCase: CancelTourUseCase,
    private val getTourUseCase: GetTourUseCase,
//    private val suggestAnotherTimeUseCase: SuggestAnotherTimeUseCase,
//    private val getSupplierFreeTimeUseCase: GetSupplierFreeTimeUseCase,
//    private val getNotConfirmedTourListUseCase: GetNotConfirmedTourListUseCase
) {

    @SecurityRequirement(name = "security_auth")
    @GetMapping("/{id}")
    fun get(
        @PathVariable("id") tourtId: String,
        @Parameter(hidden = true) authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        return mono {
            getTourUseCase(
                GetTourParams(
                    (authentication.principal as SessionUser).id,
                    tourtId
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
    @GetMapping
    fun getAll(
        @Parameter(hidden = true) authentication: Authentication,
        @RequestParam(required = true)
        statuses: Set<AppointmentStatus>,
        @RequestParam(required = true)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        reservationDateFrom: LocalDateTime,
        @RequestParam(required = true)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        reservationDateTo: LocalDateTime,
    ): Mono<ResponseEntity<Any>> {
        return mono { getTourListUseCase(
            GetToursParams(
                (authentication.principal as SessionUser).id,
                statuses,
                reservationDateFrom,
                reservationDateTo
            )
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
    @DeleteMapping("{id}")
    fun delete(
        @PathVariable id: String
    ): Mono<ResponseEntity<Any>> {
        return mono { deleteTourUseCase(DeleteTourParams(id)) }.map {
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

//    @SecurityRequirement(name = "security_auth")
//    @PutMapping("/{id}/cancel")
//    fun cancel(
//        @PathVariable id: String
//    ): Mono<ResponseEntity<Any>> {
//        return mono { cancelTourUseCase(CancelTourParams(id)) }.map {
//            when (it) {
//                is Data.Success -> {
//                    ResponseEntity.ok().build()
//                }
//                is Data.Error -> {
//                    ResponseEntity.status(it.failure.code).body(it.failure)
//                }
//            }
//        }
//    }
//
//    @SecurityRequirement(name = "security_auth")
//    @PutMapping("/{id}/complete")
//    fun complete(
//        @PathVariable id: String
//    ): Mono<ResponseEntity<Any>> {
//        return mono { completeTourUseCase(CompleteTourParams(id)) }.map {
//            when (it) {
//                is Data.Success -> {
//                    ResponseEntity.ok().build()
//                }
//                is Data.Error -> {
//                    ResponseEntity.status(it.failure.code).body(it.failure)
//                }
//            }
//        }
//    }
//
//    @SecurityRequirement(name = "security_auth")
//    @PutMapping("/{id}/confirm")
//    fun confirm(
//        @PathVariable id: String
//    ): Mono<ResponseEntity<Any>> {
//        return mono { confirmTourUseCase(ConfirmTourParams(id)) }.map {
//            when (it) {
//                is Data.Success -> {
//                    ResponseEntity.ok().build()
//                }
//                is Data.Error -> {
//                    ResponseEntity.status(it.failure.code).body(it.failure)
//                }
//            }
//        }
//    }

//    @SecurityRequirement(name = "security_auth")
//    @PutMapping("/{id}/suggest-another-time")
//    fun suggestAnotherTime(
//        @Parameter(hidden = true) request: ServerHttpRequest,
//        authentication: Authentication,
//        @PathVariable id: String,
//        @RequestParam
//        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
//        reservationDateTime: LocalDateTime
//    ): Mono<ResponseEntity<Any>> {
//
//        val user = authentication.principal as SessionUser
//        val userId = user.id
//
//        return mono { suggestAnotherTimeUseCase(
//            SuggestAnotherTimeParams(
//                userId, id, reservationDateTime
//            )
//        ) }.map {
//            when (it) {
//                is Data.Success -> {
//                    ResponseEntity.ok().build()
//                }
//                is Data.Error -> {
//                    ResponseEntity.status(it.failure.code).body(it.failure)
//                }
//            }
//        }
//    }
//
//    @SecurityRequirement(name = "security_auth")
//    @GetMapping("/get-free-hours")
//    fun getFreeTime(
//        @Parameter(hidden = true) request: ServerHttpRequest,
//        authentication: Authentication,
//        @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate
//    ): Mono<ResponseEntity<Any>> {
//        val user = authentication.principal as SessionUser
//        val userId = user.id
//
//        return mono { getSupplierFreeTimeUseCase(
//            GetSupplierFreeTimeParams(userId, date)
//        ) }.map {
//            when (it) {
//                is Data.Success -> {
//                    ResponseEntity.ok().body(it.data)
//                }
//                is Data.Error -> {
//                    ResponseEntity.status(it.failure.code).body(it.failure)
//                }
//            }
//        }
//    }
//
//    @SecurityRequirement(name = "security_auth")
//    @GetMapping("/not-confirmed")
//    fun getNotConfirmed(
//        @Parameter(hidden = true) authentication: Authentication,
//        @RequestParam(required = false, defaultValue = "0")
//        page: Int,
//        @RequestParam(required = false, defaultValue = "10")
//        size: Int,
//    ): Mono<ResponseEntity<Any>> {
//        return mono { getNotConfirmedTourListUseCase(
//            GetNotConfirmedTourListParams(
//                (authentication.principal as SessionUser).id,
//                page,
//                size,
//            )
//        ) }.map {
//            when (it) {
//                is Data.Success -> {
//                    ResponseEntity.ok().body(it.data)
//                }
//                is Data.Error -> {
//                    ResponseEntity.status(it.failure.code).body(it.failure)
//                }
//            }
//        }
//    }

}