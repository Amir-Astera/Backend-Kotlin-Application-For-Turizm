package dev.december.jeterbackend.supplier.features.payments.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class PaymentListNotFoundFailure(
    override val code: Int = 404,
    override val message: String = "Payments not found"
) : Failure