package dev.december.jeterbackend.client.features.payments.domain.services

import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.tours.domain.models.Tour
import java.time.LocalDateTime

interface PaymentService {
    suspend fun create(name: String): Data<String>

    suspend fun getAll(
        userId: String,
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?
    ): Data<Unit>//List<Tour>
}