package dev.december.jeterbackend.admin.features.suppliers.domain.services

import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.FeedbackSortField
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.FeedbackWithGrade
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierSortField
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierStatus
import org.springframework.data.domain.Page
import java.time.LocalDateTime

interface SupplierService {
    suspend fun getList(
        sortField: SupplierSortField,
        sortDirection: SortDirection,
        page: Int,
        size: Int,
        searchField: String?,
        activityStatuses: Set<AccountActivityStatus>?,
        statuses: Set<SupplierStatus>?,
        professionIds: Set<String>?,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?,
        enableStatus: AccountEnableStatus?,
    ): Data<Page<Supplier>>
    suspend fun get(supplierId: String): Data<Supplier>
    suspend fun disableList(ids: List<String>): Data<Unit>
    suspend fun disable(id: String): Data<Unit>
    suspend fun approve(id: String): Data<Unit>
    suspend fun disapprove(id: String): Data<Unit>
    suspend fun enable(id: String): Data<Unit>
    suspend fun disableRoleSupplier(userId: String)
    suspend fun getSupplierFeedbacks(
        userId: String,
        sortField: FeedbackSortField,
        sortDirection: SortDirection,
        page: Int,
        size: Int
    ): Data<Unit>//FeedbackWithGrade
}