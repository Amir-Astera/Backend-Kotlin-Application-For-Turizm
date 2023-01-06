package dev.december.jeterbackend.admin.features.suppliers.domain.usecases

import dev.december.jeterbackend.admin.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.OsType
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierSortField
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierStatus
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class GetSupplierListUseCase(
    private val supplierService: SupplierService,
) : UseCase<GetSupplierListParams, Page<Supplier>> {
    override suspend fun invoke(params: GetSupplierListParams): Data<Page<Supplier>> {
        return supplierService.getList(
            params.sortField,
            params.sortDirection,
            params.page,
            params.size,
            params.searchField,
            params.activityStatuses,
            params.statuses,
            params.osTypes,
            params.professionIds,
            params.createdFrom,
            params.createdTo,
            params.enableStatus,
        )
    }

}

data class GetSupplierListParams(
    val sortField: SupplierSortField,
    val sortDirection: SortDirection,
    val page: Int,
    val size: Int,
    val searchField: String?,
    val activityStatuses: Set<AccountActivityStatus>?,
    val statuses: Set<SupplierStatus>?,
    val osTypes: Set<OsType>?,
    val professionIds: Set<String>?,
    val createdFrom: LocalDateTime?,
    val createdTo: LocalDateTime?,
    val enableStatus: AccountEnableStatus?,
)