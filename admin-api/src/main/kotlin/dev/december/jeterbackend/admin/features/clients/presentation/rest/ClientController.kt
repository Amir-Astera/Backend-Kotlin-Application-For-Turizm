package dev.december.jeterbackend.admin.features.clients.presentation.rest

import dev.december.jeterbackend.admin.features.clients.domain.usecases.*
import dev.december.jeterbackend.admin.features.clients.presentation.dto.DeleteClientListData
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.OsType
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.clients.domain.models.ClientSortField
import dev.december.jeterbackend.shared.features.orders.domain.models.CommunicationType
import dev.december.jeterbackend.shared.features.tours.domain.models.TourStatus
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.mono
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/clients")
@Tag(name = "clients", description = "The Client API")
class ClientController(
    private val getClientListUseCase: GetClientListUseCase,
//    private val createClientUseCase: CreateClientUseCase,
    private val getClientUseCase: GetClientUseCase,
    private val patchClientUseCase: PatchClientUseCase,
    private val deleteClientByUserIdUseCase: DeleteClientByUserIdUseCase,
    private val deleteClientUseCase: DeleteClientUseCase,
    private val deleteByClientIdsUseCase: DeleteByClientIdsUseCase,
    private val enableClientUseCase: EnableClientUseCase,
) {

    @SecurityRequirement(name = "security_auth")
    @GetMapping
    fun getList(
        @RequestParam(required = false, defaultValue = "CREATED_AT")
        sortField: ClientSortField,
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
        osTypes: Set<OsType>?,
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
            getClientListUseCase(
                GetClientListParams(
                    sortField,
                    sortDirection,
                    page,
                    size,
                    searchField,
                    activityStatuses,
                    osTypes,
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
    @PutMapping("/disable/{id}")
    fun disable(
        @PathVariable id: String,
    ) : Mono<ResponseEntity<Any>>{
        return mono { deleteClientUseCase(DeleteClientParams(id)) }.map {
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
    @PatchMapping("/disable")
    fun disableList(
        @RequestBody data: DeleteClientListData,
    ) : Mono<ResponseEntity<Any>>{
        return mono { deleteByClientIdsUseCase(DeleteByClientIdsParams(data.ids)) }.map {
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
    @PutMapping(value = ["/{id}/enable"])
    fun enable(
        @PathVariable id: String,
    ): Mono<ResponseEntity<Any>> {
        return mono { enableClientUseCase(EnableClientParams(id)) }.map {
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

}