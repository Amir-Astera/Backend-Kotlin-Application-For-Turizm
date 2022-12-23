package dev.december.jeterbackend.admin.features.suppliers.domain.usecases

import dev.december.jeterbackend.admin.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.FeedbackSortField
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.FeedbackWithGrade
import org.springframework.stereotype.Component

@Component
class GetSupplierFeedbacksUseCase(
    private val supplierService: SupplierService
): UseCase<GetSupplierFeedbacksParams, FeedbackWithGrade> {
    override suspend fun invoke(params: GetSupplierFeedbacksParams): Data<FeedbackWithGrade> {
        return supplierService.getSupplierFeedbacks(params.userId, params.sortField, params.sortDirection, params.page, params.size)
    }
}

data class GetSupplierFeedbacksParams(
    val userId: String,
    val sortField: FeedbackSortField,
    val sortDirection: SortDirection,
    val page: Int,
    val size: Int
)