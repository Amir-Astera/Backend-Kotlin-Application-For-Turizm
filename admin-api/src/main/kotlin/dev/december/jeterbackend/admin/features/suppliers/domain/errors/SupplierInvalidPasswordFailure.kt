package dev.december.jeterbackend.admin.features.suppliers.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class SupplierInvalidPasswordFailure(
    override val code: Int = 400,
    override val message: String = "Incorrect password!"
) : Failure