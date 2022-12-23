package dev.december.jeterbackend.client.features.orders.domain.services

import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.orders.domain.models.CommunicationType


interface OrderService {
    suspend fun pay(clientId: String, supplierId: String, cardCryptogram: String, communicationType: CommunicationType, ipAddress: String): Data<Unit>//Order
    suspend fun payWithSecure(md: Long, paRes: String, clientId: String): Data<Unit>//Order
}