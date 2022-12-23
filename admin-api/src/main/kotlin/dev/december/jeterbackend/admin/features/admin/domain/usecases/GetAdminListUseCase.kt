package dev.december.jeterbackend.admin.features.admin.domain.usecases

import dev.december.jeterbackend.admin.features.admin.domain.services.AdminService
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.admin.domain.models.Admin
import dev.december.jeterbackend.shared.features.admin.domain.models.AdminAuthorityCode
import dev.december.jeterbackend.shared.features.admin.domain.models.AdminSortField
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class GetAdminListUseCase(
    private val adminService: AdminService,
) : UseCase<GetAdminListParams, Page<Admin>> {
    override suspend fun invoke(params: GetAdminListParams): Data<Page<Admin>> {
        return adminService.getList(
            params.sortField,
            params.sortDirection,
            params.page,
            params.size,
            params.searchField,
            params.authorityCodes,
            params.activityStatuses,
            params.enableStatus,
            params.createdFrom,
            params.createdTo,
        )
    }

}

data class GetAdminListParams(
    val sortField: AdminSortField,
    val sortDirection: SortDirection,
    val page: Int,
    val size: Int,
    val searchField: String?,
    val authorityCodes: Set<AdminAuthorityCode>,
    val activityStatuses: Set<AccountActivityStatus>?,
    val enableStatus: AccountEnableStatus?,
    val createdFrom: LocalDateTime?,
    val createdTo: LocalDateTime?,
)