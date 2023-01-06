package dev.december.jeterbackend.client.features.payments.domain.usecases

import dev.december.jeterbackend.client.features.payments.domain.services.PaymentService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.appointments.domain.models.Appointment
import dev.december.jeterbackend.shared.features.tours.domain.models.Tour
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class GetPaymentListUseCase(
    private val paymentService: PaymentService
) : UseCase<GetPaymentListParams, Page<Appointment>> {
    override suspend fun invoke(params: GetPaymentListParams): Data<Page<Appointment>> {
        return paymentService.getAll(
            params.userId,
            params.page,
            params.size,
            params.createdFrom,
            params.createdTo
        )
    }
}

data class GetPaymentListParams(
    val userId: String,
    val page: Int,
    val size: Int,
    val createdFrom: LocalDateTime?,
    val createdTo: LocalDateTime?
)