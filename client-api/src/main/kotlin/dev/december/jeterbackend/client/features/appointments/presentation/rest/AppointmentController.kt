package dev.december.jeterbackend.client.features.appointments.presentation.rest

import dev.december.jeterbackend.client.core.config.security.SessionUser
import dev.december.jeterbackend.client.features.appointments.domain.usecases.*
import dev.december.jeterbackend.client.features.appointments.presentation.dto.CreateAppointmentData
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentStatus
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
@RequestMapping("/api/appointment")
@Tag(name = "appointments", description = "The Appointments API")
class AppointmentController(
    private val createAppointmentUseCase: CreateAppointmentUseCase,
    private val getAppointmentListUseCase: GetAppointmentListUseCase,
    private val deleteAppointmentUseCase: DeleteAppointmentUseCase,
    private val confirmAppointmentUseCase: ConfirmAppointmentUseCase,
    private val completeAppointmentUseCase: CompleteAppointmentUseCase,
    private val cancelAppointmentUseCase: CancelAppointmentUseCase,
    private val getAppointmentUseCase: GetAppointmentUseCase,
    private val suggestAnotherTimeUseCase: SuggestAnotherTimeUseCase
) {

    @SecurityRequirement(name = "security_auth")
    @PostMapping
    fun create(
        @RequestBody data: CreateAppointmentData,
        @Parameter(hidden = true) authentication: Authentication,
        @Parameter(hidden = true) request: ServerHttpRequest,
    ): Mono<ResponseEntity<Any>> {
        return mono { createAppointmentUseCase(data.convert(mapOf("userId" to (authentication.principal as SessionUser).id))) }.map {
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
    @GetMapping("/{id}")
    fun get(
        @PathVariable("id") appointmentId: String,
        @Parameter(hidden = true) authentication: Authentication,
    ): Mono<ResponseEntity<Any>> {
        return mono {
            getAppointmentUseCase(
                GetAppointmentParams(
                    (authentication.principal as SessionUser).id,
                    appointmentId
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
        return mono { getAppointmentListUseCase(
            GetAppointmentsParams(
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
    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: String
    ): Mono<ResponseEntity<Any>> {
        return mono { deleteAppointmentUseCase(DeleteAppointmentParams(id)) }.map {
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
    @PutMapping("/{id}/cancel")
    fun cancel(
        @PathVariable id: String
    ): Mono<ResponseEntity<Any>> {
        return mono { cancelAppointmentUseCase(CancelAppointmentParams(id)) }.map {
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
    @PutMapping("/{id}/complete")
    fun complete(
        @PathVariable id: String
    ): Mono<ResponseEntity<Any>> {
        return mono { completeAppointmentUseCase(CompleteAppointmentParams(id)) }.map {
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
    @PutMapping("/{id}/confirm")
    fun confirm(
        @PathVariable id: String
    ): Mono<ResponseEntity<Any>> {
        return mono { confirmAppointmentUseCase(ConfirmAppointmentParams(id)) }.map {
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
    @PutMapping("/{id}/suggest-another-time")
    fun suggestAnotherTime(
        @PathVariable id: String,
        @RequestParam
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        reservationDateTime: LocalDateTime
    ): Mono<ResponseEntity<Any>> {
        return mono {
            suggestAnotherTimeUseCase(
                SuggestAnotherTimeParams(
                    id, reservationDateTime
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