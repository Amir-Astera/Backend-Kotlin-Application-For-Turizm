package dev.december.jeterbackend.admin.features.clients.domain.services

import dev.december.jeterbackend.admin.features.clients.presentation.dto.UpdateClientData
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.domain.model.UserGender
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import dev.december.jeterbackend.shared.features.clients.domain.models.ClientSortField
import dev.december.jeterbackend.shared.features.clients.domain.models.ClientUser
import org.springframework.data.domain.Page
import java.time.LocalDate
import java.time.LocalDateTime

interface ClientService {
    suspend fun getList(
        sortField: ClientSortField,
        sortDirection: SortDirection,
        page: Int,
        size: Int,
        searchField: String?,
        activityStatuses: Set<AccountActivityStatus>?,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?,
        enableStatus: AccountEnableStatus?
    ): Data<Page<Client>>

//    suspend fun create(
//        userId: String,
//        login: String,
//        fullName: String,
//        birthDate: LocalDate?,
//        gender: UserGender?,
//        avatarId: String?,
//        registrationToken: String?
//    ): Data<String>

    suspend fun get(
        userId: String
    ): Data<ClientUser>

    suspend fun update(
        userId: String,
        updateClientData: UpdateClientData?
    ): Data<String>

    suspend fun deleteByUserId(
        userId: String
    ): Data<String>

    suspend fun delete(
        ids: String
    ): Data<Unit>

    suspend fun deleteList(
        ids: Set<String>
    ): Data<Unit>

    suspend fun enable(id: String): Data<Unit>
}