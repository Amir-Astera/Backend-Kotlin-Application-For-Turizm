package dev.december.jeterbackend.shared.features.orders.domain.models

import com.fasterxml.jackson.annotation.*
import java.util.*

data class OrderData(
    @JsonProperty("Amount") val amount: Int,
    @JsonProperty("Currency") val currency: String,
    @JsonProperty("InvoiceId") val invoiceId: String,
    @JsonProperty("IpAddress") val ipAddress: String,
    @JsonProperty("Description") val description: String,
    @JsonProperty("AccountId") val accountId: String,
    @JsonProperty("Email") val email: String? = null,
    @JsonProperty("CardCryptogramPacket") val cardCryptogramPacket: String
)