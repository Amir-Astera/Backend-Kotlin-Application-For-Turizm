package dev.december.jeterbackend.client.features.orders.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class OrderPayWithSecureException(
    override val code: Int = 500,
    override val message: String = "Cannot pay with secure!"
): Failure