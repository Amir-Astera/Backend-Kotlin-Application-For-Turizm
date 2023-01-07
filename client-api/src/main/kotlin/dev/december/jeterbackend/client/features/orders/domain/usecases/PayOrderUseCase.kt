package dev.december.jeterbackend.client.features.orders.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.client.features.orders.domain.services.OrderService
import dev.december.jeterbackend.shared.features.orders.domain.models.CommunicationType
import dev.december.jeterbackend.shared.features.orders.domain.models.Order
import org.springframework.stereotype.Component


@Component
class PayOrderUseCase(
    private val orderService: OrderService
) : UseCase<PayOrderParams, Unit> {
    override suspend fun invoke(params: PayOrderParams): Data<Unit> {//Order
        return orderService.pay(params. clientId, params.supplierId, params.cardCryptogram, params.communicationType, params.ipAddress)
    }
}

data class PayOrderParams(
    val clientId: String,
    val supplierId: String,
    val cardCryptogram: String,
    val communicationType: CommunicationType,
    val ipAddress: String
)