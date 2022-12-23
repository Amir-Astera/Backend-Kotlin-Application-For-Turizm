package dev.december.jeterbackend.supplier.features.suppliers.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class SupplierListGetFailure(
    override val code: Int = 500,
    override val message: String = "Cannot get suppliers!"
) : Failure
