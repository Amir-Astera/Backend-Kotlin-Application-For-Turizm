package dev.december.jeterbackend.client.features.authorization.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class SupplierAuthFailure(
    override val code: Int = 401,
    override val message: String = "Authorization failed!"
) : Failure
