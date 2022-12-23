package dev.december.jeterbackend.client.features.orders.presentation.dta

import com.fasterxml.jackson.annotation.JsonProperty

data class SecureOrderData(
    @JsonProperty("MD") val md: Long,
    @JsonProperty("PaRes") val paRes: String
)