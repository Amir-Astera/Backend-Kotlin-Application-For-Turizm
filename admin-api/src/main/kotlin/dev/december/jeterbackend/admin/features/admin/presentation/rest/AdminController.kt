package dev.december.jeterbackend.admin.features.admin.presentation.rest

import dev.december.jeterbackend.admin.core.config.security.SessionUser
import dev.december.jeterbackend.admin.features.admin.presentation.dto.CreateAdminData
import dev.december.jeterbackend.admin.features.admin.presentation.dto.UpdateAdminByUserIdData
import dev.december.jeterbackend.admin.features.admin.presentation.dto.UpdateAdminData
import dev.december.jeterbackend.admin.features.admin.domain.usecases.*
import dev.december.jeterbackend.admin.features.admin.presentation.dto.DeleteAdminListData
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.admin.domain.models.AdminAuthorityCode
import dev.december.jeterbackend.shared.features.admin.domain.models.AdminSortField
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.mono
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.net.URI
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/admins")
@Tag(name = "admin", description = "The Admin API")
class AdminController(
    private val getAdminListUseCase: GetAdminListUseCase,
    private val createAdminUseCase: CreateAdminUseCase,
    private val deleteAdminByUserIdUseCase: DeleteAdminByUserIdUseCase,
    private val deleteAdminUseCase: DeleteAdminUseCase,
    private val deleteByAdminIdsUseCase: DeleteByAdminIdsUseCase,
    private val patchAdminByUserIdUseCase: PatchAdminByUserIdUseCase,
    private val patchAdminUseCase: PatchAdminUseCase,
    private val getCurrentAdminUseCase: GetCurrentAdminUseCase,
) {

    @SecurityRequirement(name = "security_auth")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping
    fun create(
        @RequestBody admin: CreateAdminData,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication
    ): Mono<ResponseEntity<Any>> {
        return mono { createAdminUseCase(admin.convert()) }.map {
            when (it) {
                is Data.Success -> {
                    ResponseEntity.created(URI.create("${request.uri}/${it.data}")).build()
                }
                is Data.Error -> {
                    ResponseEntity.status(it.failure.code).body(it.failure.message)
                }
            }
        }
    }

    @SecurityRequirement(name = "security_auth")
    @GetMapping
    fun getList(
        @RequestParam(required = false, defaultValue = "CREATED_AT")
        sortField: AdminSortField,
        @RequestParam(required = false, defaultValue = "DESC")
        sortDirection: SortDirection,
        @RequestParam(required = false, defaultValue = "0")
        page: Int,
        @RequestParam(required = false, defaultValue = "10")
        size: Int,
        @RequestParam(required = false)
        searchField: String?,
        @RequestParam(required = false)
        authorityCodes: Set<AdminAuthorityCode>,
        @RequestParam(required = false)
        activityStatuses: Set<AccountActivityStatus>?,
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
            getAdminListUseCase(
                GetAdminListParams(
                    sortField,
                    sortDirection,
                    page,
                    size,
                    searchField,
                    authorityCodes,
                    activityStatuses,
                    enableStatus,
                    createdFrom,
                    createdTo,
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
    @GetMapping("/current")
    fun getCurrent(
        authentication: Authentication
    ) : Mono<ResponseEntity<Any>>{
        val user = authentication.principal as SessionUser
        val adminId = user.adminId

        return mono { getCurrentAdminUseCase(GetCurrentAdminParams(adminId)) }.map {
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
    @DeleteMapping("/current")
    fun deleteCurrent(
        authentication: Authentication
    ) : Mono<ResponseEntity<Any>>{
        val user = authentication.principal as SessionUser
        val adminId = user.adminId

        return mono { deleteAdminByUserIdUseCase(DeleteAdminByUserIdParams(adminId)) }.map {
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
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: String,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication
    ) : Mono<ResponseEntity<Any>>{
        return mono { deleteAdminUseCase(DeleteAdminParams(id)) }.map {
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
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @DeleteMapping
    fun deleteList(
        @RequestBody data: DeleteAdminListData,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication
    ) : Mono<ResponseEntity<Any>>{

        return mono { deleteByAdminIdsUseCase(DeleteByAdminIdsParams(data.ids)) }.map {
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
    @PatchMapping("/current")
    fun updateCurrent(
        @RequestBody updateAdminByUserIdData: UpdateAdminByUserIdData,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication
    ): Mono<ResponseEntity<Any>>{
        val user = authentication.principal as SessionUser
        val adminId = user.adminId

        return mono { patchAdminByUserIdUseCase(PatchAdminByUserIdParams(adminId, updateAdminByUserIdData)) } .map {
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
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PatchMapping("/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody updateAdminData: UpdateAdminData,
        @Parameter(hidden = true) request: ServerHttpRequest,
        authentication: Authentication
    ): Mono<ResponseEntity<Any>>{
        return mono { patchAdminUseCase(PatchAdminParams(id, updateAdminData)) } .map {
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