package dev.december.jeterbackend.shared.features.payments.domain.models

import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import org.springframework.data.domain.Page

interface PaymentBySupplierInterface {
    fun getClient(): String
    fun getPayment(): Int?
    fun getSessionCount(): Int?
}

data class PaymentBySupplier(
    val client: Client,
    val payment: Int,
    val sessionCount: Int
)

data class PaymentBySupplierList(
    val total: Int,
    val payments: Page<PaymentBySupplier>
)