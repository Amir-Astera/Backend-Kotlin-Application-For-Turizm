package dev.december.jeterbackend.supplier.features.payments.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class PaymentCreateFailure(
    override val code: Int = 500,
    override val message: String = "Cannot create payment"
) : Failure