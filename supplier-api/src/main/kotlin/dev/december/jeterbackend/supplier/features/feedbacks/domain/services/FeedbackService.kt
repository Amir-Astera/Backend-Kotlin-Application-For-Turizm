package dev.december.jeterbackend.supplier.features.feedbacks.domain.services

import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.core.results.Data
import java.time.LocalDateTime

interface FeedbackService {

    suspend fun create(
        grade: Float,
        description: String?,
        clientId: String,
        supplierId: String,
    ): Data<String>

    suspend fun delete(
        id: String,
    ): Data<Unit>

    suspend fun updateSupplierRating(
        supplier: SupplierEntity,
    )

    suspend fun getList(
        id: String,
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?,
    ): Data<Unit>//Page<FeedbackSupplier>

}