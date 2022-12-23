package dev.december.jeterbackend.supplier.features.suppliers.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class SupplierAlreadyExistsFailure(
    override val code: Int = 400,
    override val message: String = "Supplier already exists!"
) : Failure
