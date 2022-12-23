package dev.bytepride.truprobackend.admin.features.suppliers.presentation.rest

import dev.bytepride.truprobackend.admin.features.appointments.domain.usecases.*
import dev.bytepride.truprobackend.core.domain.model.SortDirection
import dev.bytepride.truprobackend.core.results.Data
import dev.bytepride.truprobackend.core.utils.convert
import dev.bytepride.truprobackend.features.suppliers.domain.models.*
import dev.bytepride.truprobackend.admin.features.suppliers.domain.usecases.*
import dev.bytepride.truprobackend.admin.features.suppliers.presentation.dto.DisableSupplierListData
import dev.bytepride.truprobackend.core.domain.model.AccountActivityStatus
import dev.bytepride.truprobackend.core.domain.model.AccountEnableStatus
import dev.bytepride.truprobackend.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentSortField
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentStatus
import dev.december.jeterbackend.shared.features.appointments.domain.models.CommunicationType
import dev.december.jeterbackend.admin.features.suppliers.domain.usecases.*
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.mono
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
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
//    private val deleteSupplierUseCase: DeleteSupplierUseCase,
//    private val deleteSupplierListUseCase: DeleteSupplierListUseCase,
    private val enableSupplierUseCase: EnableSupplierUseCase,
//    private val createSupplierUseCase: CreateSupplierUseCase,
//    private val patchSocialMediaUseCase: PatchSocialMediaUseCase,
    private val getSupplierByIdUseCase: GetSupplierByIdUseCase,
    private val getAppointmentListUseCase: GetAppointmentListUseCase,
    private val getAppointmentUseCase: GetAppointmentUseCase,
    private val getAppointmentListByUserIdUseCase: GetAppointmentListByUserIdUseCase,
    private val getAppointmentByUserIdUseCase: GetAppointmentByUserIdUseCase,
    private val confirmAppointmentUseCase: ConfirmAppointmentUseCase,
    private val suggestAnotherTimeUseCase: SuggestAnotherTimeUseCase,
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

//    @SecurityRequirement(name = "security_auth")
//    @DeleteMapping(value = ["/{id}"])
//    fun delete(
//        @PathVariable id: String,
//    ): Mono<ResponseEntity<Any>> {
//        return mono { deleteSupplierUseCase(DeleteSupplierParams(id)) }.map {
//            when (it) {
//                is Data.Success -> {
//                    ResponseEntity.ok().build()
//                }
//                is Data.Error -> {
//                    ResponseEntity.status(it.failure.code).body(it.failure.message)
//                }
//            }
//        }
//    }

//    @SecurityRequirement(name = "security_auth")
//    @DeleteMapping
//    fun deleteList(
//        @RequestBody data: DeleteSupplierListData,
//    ): Mono<ResponseEntity<Any>> {
//        return mono { deleteSupplierListUseCase(data.convert()) }.map {
//            when (it) {
//                is Data.Success -> {
//                    ResponseEntity.ok().build()
//                }
//                is Data.Error -> {
//                    ResponseEntity.status(it.failure.code).body(it.failure.message)
//                }
//            }
//        }
//    }

//    @SecurityRequirement(name = "security_auth")
//    @PostMapping
//    fun create(
//        @RequestBody supplier: CreateSupplierData,
//        @Parameter(hidden = true) request: ServerHttpRequest,
//        authentication: Authentication
//        ): Mono<ResponseEntity<Any>>{
//
//        val user = authentication.principal as SessionUser
//        val userId = user.id
//        val login = user.login
//
//
//        return mono { createSupplierUseCase(supplier.convert(mapOf("userId" to userId, "login" to login))) } .map {
//            when (it) {
//                    is Data.Success -> {
//                    ResponseEntity.created(URI.create("${request.uri}/${it.data}")).build()
//                }
//                    is Data.Error -> {
//                    ResponseEntity.status(it.failure.code).body(it.failure.message)
//                }
//            }
//        }
//    }

//    @SecurityRequirement(name = "security_auth")
//    @PatchMapping("/socialMedia")
//    fun updateSocialMedia(
//        @RequestBody socialMedia: SupplierSocialMedia,
//        @Parameter(hidden = true) request: ServerHttpRequest,
//        authentication: Authentication
//    ): Mono<ResponseEntity<Any>>{
//        val user = authentication.principal as SessionUser
//        val userId = user.id
//
//        return mono { patchSocialMediaUseCase(PatchSocialMediaParams(userId, socialMedia)) }.map {
//            when (it) {
//                is Data.Success -> {
//                    ResponseEntity.ok().body(it.data)
//                }
//                is Data.Error -> {
//                    ResponseEntity.status(it.failure.code).body(it.failure.message)
//                }
//            }
//        }
//    }

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

//    TODO for supplier api
//    @SecurityRequirement(name = "security_auth")
//    @GetMapping("/current/appointments")
//    fun getAppointmentListByCurrentUser(
//        @Parameter(hidden = true) request: ServerHttpRequest,
//        @Parameter(hidden = true) authentication: Authentication,
//        @RequestParam(required = false)
//        communicationTypes: Set<CommunicationType>?,
//        @RequestParam(required = false)
//        statuses: Set<AppointmentStatus>?,
//        @RequestParam(required = false, defaultValue = "CREATED_AT")
//        sortField: AppointmentSortField,
//        @RequestParam(required = false, defaultValue = "DESC")
//        sortDirection: SortDirection,
//        @RequestParam(required = false, defaultValue = "0")
//        page: Int,
//        @RequestParam(required = false, defaultValue = "10")
//        size: Int,
//        @RequestParam(required = false)
//        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
//        createdFrom: LocalDateTime?,
//        @RequestParam(required = false)
//        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
//        createdTo: LocalDateTime?,
//    ): Mono<ResponseEntity<Any>> {
//        val user = authentication.principal as SessionUser
//        val userId = user.id
//        return mono {
//            getAppointmentListByUserIdUseCase(
//                GetAppointmentsByUserIdParams(
//                    userId,
//                    PlatformRole.SUPPLIER,
//                    communicationTypes,
//                    statuses,
//                    sortField,
//                    sortDirection,
//                    page,
//                    size,
//                    createdFrom,
//                    createdTo
//                )
//            )
//        }.map {
//            when (it) {
//                is Data.Success -> {
//                    ResponseEntity.ok().body(it.data)
//                }
//                is Data.Error -> {
//                    ResponseEntity.status(it.failure.code).body(it.failure.message)
//                }
//            }
//        }
//    }
//
//    @SecurityRequirement(name = "security_auth")
//    @GetMapping("/current/appointment/{appointment_id}")
//    fun getAppointmentByCurrentUser(
//        @Parameter(hidden = true) request: ServerHttpRequest,
//        @Parameter(hidden = true) authentication: Authentication,
//        @PathVariable("appointment_id") appointmentId: String,
//    ): Mono<ResponseEntity<Any>> {
//        val user = authentication.principal as SessionUser
//        val userId = user.id
//        return mono {
//            getAppointmentByUserIdUseCase(
//                GetAppointmentByUserIdParams(
//                    userId,
//                    PlatformRole.SUPPLIER,
//                    appointmentId
//                )
//            )
//        }.map {
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
//    @PutMapping("/appointment/{id}/confirm")
//    fun confirm(
//        @PathVariable id: String
//    ): Mono<ResponseEntity<Any>> {
//        return mono { confirmAppointmentUseCase(ConfirmAppointmentParams(id, PlatformRole.CLIENT)) }.map {
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
//    @PutMapping("/appointment/{id}/suggest-another-time")
//    fun suggestAnotherTime(
//        @PathVariable id: String,
//        @RequestParam reservationDateTime: LocalDateTime
//    ): Mono<ResponseEntity<Any>> {
//        return mono { suggestAnotherTimeUseCase(
//            SuggestAnotherTimeParams(
//                id, PlatformRole.CLIENT, reservationDateTime
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
//    @GetMapping(value = ["/getFeedbacks"])
//    fun getSupplierFeedbacks(
//        @RequestParam(required = false, defaultValue = "CREATED_AT")
//        sortField: FeedbackSortField,
//        @RequestParam(required = false, defaultValue = "DESC")
//        sortDirection: SortDirection,
//        @RequestParam(required = false, defaultValue = "0")
//        page: Int,
//        @RequestParam(required = false, defaultValue = "10")
//        size: Int,
//        @Parameter(hidden = true) request: ServerHttpRequest,
//        @Parameter(hidden = true) authentication: Authentication,
//    ): Mono<ResponseEntity<Any>>{
//        val user = authentication.principal as SessionUser
//        val userId = user.id
//        return mono {
//            getSupplierFeedbacksUseCase(GetSupplierFeedbacksParams(
//                userId,
//                sortField,
//                sortDirection,
//                page,
//                size
//            ))
//        }.map {
//            when (it) {
//                is Data.Success -> {
//                    ResponseEntity.ok().body(it.data)
//                }
//                is Data.Error -> {
//                    ResponseEntity.status(it.failure.code).body(it.failure.message)
//                }
//            }
//        }
//    }
}