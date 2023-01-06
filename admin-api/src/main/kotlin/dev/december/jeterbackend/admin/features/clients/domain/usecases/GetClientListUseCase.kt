package dev.december.jeterbackend.admin.features.clients.domain.usecases

import dev.december.jeterbackend.admin.features.clients.domain.services.ClientService
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.OsType
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import dev.december.jeterbackend.shared.features.clients.domain.models.ClientSortField
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class GetClientListUseCase(
    private val clientService: ClientService
) : UseCase<GetClientListParams, Page<Client>> {
    override suspend fun invoke(params: GetClientListParams): Data<Page<Client>> {
        return clientService.getList(
            params.sortField,
            params.sortDirection,
            params.page,
            params.size,
            params.searchField,
            params.activityStatuses,
            params.osTypes,
            params.createdFrom,
            params.createdTo,
            params.enableStatus,
        )
    }
}

data class GetClientListParams(
    val sortField: ClientSortField,
    val sortDirection: SortDirection,
    val page: Int,
    val size: Int,
    val searchField: String?,
    val activityStatuses: Set<AccountActivityStatus>?,
    val osTypes: Set<OsType>?,
    val createdFrom: LocalDateTime?,
    val createdTo: LocalDateTime?,
    val enableStatus: AccountEnableStatus?,
)