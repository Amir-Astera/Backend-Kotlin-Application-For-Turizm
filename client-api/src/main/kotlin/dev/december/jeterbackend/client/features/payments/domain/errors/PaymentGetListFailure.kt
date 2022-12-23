package dev.december.jeterbackend.client.features.payments.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class PaymentGetListFailure(
    override val code: Int = 500,
    override val message: String = "Cannot get payment list"
) : Failure