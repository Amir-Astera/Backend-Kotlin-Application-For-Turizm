package dev.december.jeterbackend.supplier.features.suppliers.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class SupplierRegistrationTokenUpdateFailure(
    override val code: Int = 500,
    override val message: String = "Can not update registration token",
) : Failure