package dev.december.jeterbackend.client.features.feedbacks.domain.services

import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.core.results.Data
import java.time.LocalDateTime

interface FeedbackService {

    suspend fun create(
        userId: String,
        supplierId: String,
        grade: Float,
        description: String?,
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
    ): Data<Unit>//Page<Feedback>

    suspend fun getListBySupplier(
        supplierId: String,
        page: Int,
        size: Int
    ): Data<Unit>//Page<Feedback>
}