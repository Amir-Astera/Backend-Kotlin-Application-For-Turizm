package dev.december.jeterbackend.supplier.features.payments.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class SessionNotFoundFailure(
    override val code: Int = 404,
    override val message: String = "Session not found"
) : Failure