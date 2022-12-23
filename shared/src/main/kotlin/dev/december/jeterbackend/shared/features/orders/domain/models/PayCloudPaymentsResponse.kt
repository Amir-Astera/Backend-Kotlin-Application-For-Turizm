package dev.december.jeterbackend.shared.features.orders.domain.models

import com.fasterxml.jackson.annotation.JsonProperty

data class PayCloudPaymentsResponse (
    @JsonProperty("Model") val model: Model? = null,
    @JsonProperty("Success") val success: Boolean,
    @JsonProperty("Message") val message: String? = null
)

data class Model(
    @JsonProperty("ReasonCode") val reasonCode: Int? = null,
    @JsonProperty("PublicId") val publicId: String? = null,
    @JsonProperty("TerminalUrl") val terminalUrl: String? = null,
    @JsonProperty("TransactionId") val transactionId: Int? = null,
    @JsonProperty("Amount") val amount: Int? = null,
    @JsonProperty("Currency") val currency: String? = null,
    @JsonProperty("PaymentAmount") val paymentAmount: Int? = null,
    @JsonProperty("PaymentCurrency") val paymentCurrency: String? = null,
    @JsonProperty("InvoiceId") val invoiceId: String? = null,
    @JsonProperty("AccountId") val accountId: String? = null,
//    @JsonProperty("Email") val email: String,
    @JsonProperty("Description") val description: String? = null,
    @JsonProperty("TestMode") val testMode: Boolean? = null,
    @JsonProperty("Status") val status: String? = null,
    @JsonProperty("Reason") val reason: String? = null,
    @JsonProperty("CardHolderMessage") val cardHolderMessage: String? = null,
    @JsonProperty("Token") val token: String? = null,
    @JsonProperty("SubscriptionId") val subscriptionId: Long? = null,
    @JsonProperty("PaReq") val paReq: String? = null,
    @JsonProperty("GoReq") val goReq: String? = null,
    @JsonProperty("AcsUrl") val acsUrl: String? = null
)