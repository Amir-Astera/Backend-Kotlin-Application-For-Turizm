package dev.december.jeterbackend.client.features.orders.presentation.dta

import dev.december.jeterbackend.shared.features.orders.domain.models.CommunicationType


data class PayOrderData(
    val supplierId: String,
    val cardCryptogram: String,
    val communicationType: CommunicationType
)