package dev.december.jeterbackend.shared.features.payments.domain.models

import dev.december.jeterbackend.shared.features.payments.domain.models.Payment
import org.springframework.data.domain.Page

data class PaymentList(
    val total: Int,
    val payments: Page<Payment>
)