package dev.december.jeterbackend.supplier.features.suppliers.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class SupplierOtpFailure(
    override val code: Int = 401,
    override val message: String = "Wrong token!"
) : Failure