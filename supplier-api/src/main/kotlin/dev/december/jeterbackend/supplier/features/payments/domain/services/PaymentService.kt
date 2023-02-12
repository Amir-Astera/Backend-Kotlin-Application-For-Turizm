package dev.december.jeterbackend.supplier.features.payments.domain.services

import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.payments.domain.models.PaymentBySupplierList
import dev.december.jeterbackend.shared.features.payments.domain.models.PaymentList
import java.time.LocalDateTime

interface PaymentService {
    suspend fun create(name: String): Data<String>
    suspend fun getAll(id: String, page: Int, size: Int, createdFrom: LocalDateTime?,
                       createdTo: LocalDateTime?
    ): Data<PaymentList>

    suspend fun getPaymentsHistoryByClients(id: String, page: Int, size: Int,
                                            createdFrom: LocalDateTime?, createdTo: LocalDateTime?
    ): Data<PaymentBySupplierList>

    suspend fun getPaymentsHistoryByClient(id: String, clientId: String, page: Int, size: Int,
                                           createdFrom: LocalDateTime?, createdTo: LocalDateTime?
    ): Data<PaymentList>
}