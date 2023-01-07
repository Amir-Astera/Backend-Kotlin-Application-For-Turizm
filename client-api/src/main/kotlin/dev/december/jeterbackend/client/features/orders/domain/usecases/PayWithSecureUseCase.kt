package dev.december.jeterbackend.client.features.orders.domain.usecases

import dev.december.jeterbackend.client.features.orders.domain.services.OrderService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.orders.domain.models.Order
import org.springframework.stereotype.Component

@Component
class PayWithSecureUseCase(
    private val orderService: OrderService
): UseCase<PayWithSecureParams, Unit> {
    override suspend fun invoke(params: PayWithSecureParams): Data<Unit> {//Order
        return orderService.payWithSecure(params.md, params.paRes, params.clientId)
    }
}

data class PayWithSecureParams(
    val md: Long,
    val paRes: String,
    val clientId: String,
)