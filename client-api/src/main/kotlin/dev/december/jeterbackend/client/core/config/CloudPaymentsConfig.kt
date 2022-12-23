package dev.december.jeterbackend.client.core.config

import org.springframework.stereotype.Component
import org.springframework.util.Base64Utils

@Component
data class CloudPaymentsConfig(
    val baseUrl: String = "https://api.cloudpayments.ru",
    val publicId: String = "",
    val secretKey: String = "",
    val basicAuthHeader: String = "basic " + Base64Utils.encodeToString(("$publicId:$secretKey").toByteArray())
)

