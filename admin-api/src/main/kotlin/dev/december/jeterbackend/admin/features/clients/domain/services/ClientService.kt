package dev.december.jeterbackend.admin.features.clients.domain.services

import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.OsType
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import dev.december.jeterbackend.shared.features.clients.domain.models.ClientSortField
import org.springframework.data.domain.Page
import java.time.LocalDateTime

interface ClientService {
    suspend fun getList(
        sortField: ClientSortField,
        sortDirection: SortDirection,
        page: Int,
        size: Int,
        searchField: String?,
        activityStatuses: Set<AccountActivityStatus>?,
        osTypes: Set<OsType>?,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?,
        enableStatus: AccountEnableStatus?
    ): Data<Page<Client>>

    suspend fun get(
        clientId: String
    ): Data<Client>


    suspend fun disableById(
        clientId: String
    ): Data<String>

    suspend fun disableList(
        ids: Set<String>
    ): Data<Unit>

    suspend fun enable(id: String): Data<Unit>
}