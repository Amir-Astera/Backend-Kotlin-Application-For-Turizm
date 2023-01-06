package dev.bytepride.truprobackend.admin.features.suppliers.presentation.rest

import dev.december.jeterbackend.admin.features.appointments.domain.usecases.GetAppointmentListUseCase
import dev.december.jeterbackend.admin.features.appointments.domain.usecases.GetAppointmentParams
import dev.december.jeterbackend.admin.features.appointments.domain.usecases.GetAppointmentUseCase
import dev.december.jeterbackend.admin.features.appointments.domain.usecases.GetAppointmentsParams
import dev.december.jeterbackend.admin.features.suppliers.presentation.dto.DisableSupplierListData
import dev.december.jeterbackend.admin.features.suppliers.domain.usecases.*
import dev.december.jeterbackend.shared.core.domain.model.*
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentSortField
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentStatus
import dev.december.jeterbackend.shared.features.appointments.domain.models.CommunicationType
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.FeedbackSortField
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierSortField
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierStatus
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
import java.time.LocalDateTime


@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "suppliers", description = "The Suppliers API")
class SupplierController(
    private val getSupplierListUseCase: GetSupplierListUseCase,
    private val disableSupplierListUseCase: DisableSupplierListUseCase,
    private val disableSupplierUseCase: DisableSupplierUseCase,
    private val approveSupplierUseCase: ApproveSupplierUseCase,
    private val disapproveSupplierUseCase: DisapproveSupplierUseCase,
    private val enableSupplierUseCase: EnableSupplierUseCase,
    private val getSupplierByIdUseCase: GetSupplierByIdUseCase,
    private val getAppointmentListUseCase: GetAppointmentListUseCase,
    private val getAppointmentUseCase: GetAppointmentUseCase,
    private val getSupplierFeedbacksUseCase: GetSupplierFeedbacksUseCase
) {

    @SecurityRequirement(name = "security_auth")
    @GetMapping
    fun getList(
        @RequestParam(required = false, defaultValue = "CREATED_AT")
        sortField: SupplierSortField,
        @RequestParam(required = false, defaultValue = "DESC")
        sortDirection: SortDirection,
        @RequestParam(required = false, defaultValue = "0")
        page: Int,
        @RequestParam(required = false, defaultValue = "10")
        size: Int,
        @RequestParam(required = false)
        searchField: String?,
        @RequestParam(required = false)
        activityStatuses: Set<AccountActivityStatus>?,
        @RequestParam(required = false)
        statuses: Set<SupplierStatus>?,
        @RequestParam(required = false)
        osTypes: Set<OsType>?,
        @RequestParam(required = false)
        professionIds: Set<String>?,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        createdFrom: LocalDateTime?,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        createdTo: LocalDateTime?,
        @RequestParam(required = false)
        enableStatus: AccountEnableStatus?,
    ): Mono<ResponseEntity<Any>> {
        return mono {
            getSupplierListUseCase(
                GetSupplierListParams(
                    sortField,
                    sortDirection,
                    page,
                    size,
                    searchField,
                    activityStatuses,
                    statuses,
                    osTypes,
                    professionIds,
                    createdFrom,
                    createdTo,
                    enableStatus,
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
    @PatchMapping(value = ["/disable"])
    fun disableList(
        @RequestBody data: DisableSupplierListData,
    ): Mono<ResponseEntity<Any>> {
        return mono { disableSupplierListUseCase(data.convert()) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PutMapping(value = ["/{id}/disable"])
    fun disable(
        @PathVariable id: String,
    ): Mono<ResponseEntity<Any>> {
        return mono { disableSupplierUseCase(DisableSupplierParams(id)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PutMapping(value = ["/{id}/enable"])
    fun enable(
        @PathVariable id: String,
    ): Mono<ResponseEntity<Any>> {
        return mono { enableSupplierUseCase(EnableSupplierParams(id)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PutMapping(value = ["/{id}/approve"])
    fun approve(
        @PathVariable id: String,
    ): Mono<ResponseEntity<Any>> {
        return mono { approveSupplierUseCase(ApproveSupplierParams(id)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @PutMapping(value = ["/{id}/disapprove"])
    fun disapprove(
        @PathVariable id: String,
    ): Mono<ResponseEntity<Any>> {
        return mono { disapproveSupplierUseCase(DisapproveSupplierParams(id)) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.ok().build()
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
        @PathVariable id : String,
    ): Mono<ResponseEntity<Any>> {
        return mono {
            getSupplierByIdUseCase(GetSupplierByIdParams(id))
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
        @PathVariable id: String,
        @RequestParam(required = false)
        communicationTypes: Set<CommunicationType>?,
        @RequestParam(required = false)
        statuses: Set<AppointmentStatus>?,
        @RequestParam(required = false, defaultValue = "CREATED_AT")
        sortField: AppointmentSortField,
        @RequestParam(required = false, defaultValue = "DESC")
        sortDirection: SortDirection,
        @RequestParam(required = false, defaultValue = "0")
        page: Int,
        @RequestParam(required = false, defaultValue = "10")
        size: Int,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        createdFrom: LocalDateTime?,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        createdTo: LocalDateTime?,
    ): Mono<ResponseEntity<Any>> {
        return mono {
            getAppointmentListUseCase(
                GetAppointmentsParams(id, PlatformRole.SUPPLIER, communicationTypes, statuses, sortField, sortDirection, page, size, createdFrom, createdTo)
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
    @GetMapping("/{id}/appointment/{appointment_id}")
    fun getAppointment(
        @PathVariable("id") id: String,
        @PathVariable("appointment_id") appointmentId: String,
    ): Mono<ResponseEntity<Any>> {
        return mono {
            getAppointmentUseCase(
                GetAppointmentParams(
                    id,
                    PlatformRole.SUPPLIER,
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
    @GetMapping( "{supplierId}/getFeedbacks")
    fun getSupplierFeedbacks(
        @PathVariable supplierId: String,
        @RequestParam(required = false, defaultValue = "CREATED_AT")
        sortField: FeedbackSortField,
        @RequestParam(required = false, defaultValue = "DESC")
        sortDirection: SortDirection,
        @RequestParam(required = false, defaultValue = "0")
        page: Int,
        @RequestParam(required = false, defaultValue = "10")
        size: Int,
        @Parameter(hidden = true) request: ServerHttpRequest,
        @Parameter(hidden = true) authentication: Authentication,
    ): Mono<ResponseEntity<Any>>{
        return mono {
            getSupplierFeedbacksUseCase(GetSupplierFeedbacksParams(
                supplierId,
                sortField,
                sortDirection,
                page,
                size
            ))
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
}