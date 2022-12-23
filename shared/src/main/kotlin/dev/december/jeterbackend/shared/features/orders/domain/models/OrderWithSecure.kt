package dev.december.jeterbackend.shared.features.orders.domain.models

import com.fasterxml.jackson.annotation.JsonProperty

data class OrderWithSecure(
    @JsonProperty("TransactionId") val transactionId: Int,
    @JsonProperty("PaReq") val paReq: String,
    @JsonProperty("GoReq") val goReq: String? = null,
    @JsonProperty("AcsUrl") val acsUrl: String
)

