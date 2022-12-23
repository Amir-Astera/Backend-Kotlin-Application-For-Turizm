package dev.december.jeterbackend.supplier.features.payments.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.payments.domain.services.PaymentService
import dev.december.jeterbackend.shared.features.payments.domain.models.PaymentBySupplierList
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class GetPaymentsByClientsUseCase(
    private val paymentService: PaymentService
): UseCase<GetPaymentsByClientsParams, PaymentBySupplierList> {
    override suspend fun invoke(params: GetPaymentsByClientsParams): Data<PaymentBySupplierList> {
        return paymentService.getPaymentsHistoryByClients(
            params.id, params.page, params.size,
            params.createdFrom, params.createdTo
        )
    }
}

data class GetPaymentsByClientsParams(
    val id: String,
    val page: Int,
    val size: Int,
    val createdFrom: LocalDateTime?,
    val createdTo: LocalDateTime?,
)