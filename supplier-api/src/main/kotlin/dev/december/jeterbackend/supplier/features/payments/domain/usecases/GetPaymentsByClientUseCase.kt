package dev.december.jeterbackend.supplier.features.payments.domain.usecases

import dev.december.jeterbackend.supplier.features.payments.domain.services.PaymentService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.payments.domain.models.PaymentList
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class GetPaymentsByClientUseCase(
    private val paymentService: PaymentService
): UseCase<GetPaymentsByClientParams, PaymentList> {
    override suspend fun invoke(params: GetPaymentsByClientParams): Data<PaymentList> {
        return paymentService.getPaymentsHistoryByClient(
            params.id, params.clientId, params.page, params.size,
            params.createdFrom, params.createdTo
        )
    }
}

data class GetPaymentsByClientParams(
    val id: String,
    val clientId: String,
    val page: Int,
    val size: Int,
    val createdFrom: LocalDateTime?,
    val createdTo: LocalDateTime?,
)