package dev.december.jeterbackend.supplier.features.payments.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.payments.domain.services.PaymentService
import dev.december.jeterbackend.shared.features.payments.domain.models.PaymentList
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class GetAllPaymentsUseCase(
    private val paymentService: PaymentService
): UseCase<GetAllPaymentsParams, PaymentList> {
    override suspend fun invoke(params: GetAllPaymentsParams): Data<PaymentList> {
        return paymentService.getAll(
            params.id, params.page, params.size,
            params.createdFrom, params.createdTo
        )
    }
}

data class GetAllPaymentsParams(
    val id: String,
    val page: Int,
    val size: Int,
    val createdFrom: LocalDateTime?,
    val createdTo: LocalDateTime?,
)