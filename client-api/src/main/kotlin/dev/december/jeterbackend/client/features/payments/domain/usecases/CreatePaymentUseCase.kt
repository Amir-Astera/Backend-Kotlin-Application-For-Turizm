package dev.december.jeterbackend.client.features.payments.domain.usecases

import dev.december.jeterbackend.client.features.payments.domain.services.PaymentService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component


@Component
class CreatePaymentUseCase(
    private val paymentService: PaymentService
) : UseCase<CreatePaymentParams, String> {
    override suspend fun invoke(params: CreatePaymentParams): Data<String> {
        return paymentService.create(params.name)
    }
}

data class CreatePaymentParams(
    val name: String,
)