package dev.december.jeterbackend.admin.features.feedbacks.domain.services

import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.Feedback
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.FeedbackSortField
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.FeedbackStatus
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import org.springframework.data.domain.Page
import java.time.LocalDateTime

interface FeedbackService {
    suspend fun getList(
        sortField: FeedbackSortField,
        sortDirection: SortDirection,
        page: Int,
        size: Int,
        statuses: Set<FeedbackStatus>,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?,
        grades: Set<Float>?,
    ): Data<Page<Feedback>>

    suspend fun approve(
        id: String,
    ): Data<Unit>

    suspend fun disapprove(
        id: String,
    ): Data<Unit>

    suspend fun create(
        grade: Float,
        description: String?,
        clientId: String,
        supplierId: String,
    ): Data<String>

    suspend fun undefine(
        id: String,
    ): Data<Unit>

    suspend fun delete(
        id: String,
    ): Data<Unit>

    suspend fun deleteList(
        ids: List<String>,
    ): Data<Unit>

    suspend fun disapproveList(
        ids: List<String>,
    ): Data<Unit>

    suspend fun updateSupplierRating(
        supplier: SupplierEntity,
    )
}