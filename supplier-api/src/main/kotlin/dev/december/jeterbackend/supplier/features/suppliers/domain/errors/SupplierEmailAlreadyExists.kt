package dev.december.jeterbackend.supplier.features.suppliers.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class SupplierEmailAlreadyExists(
    val email: String,
    override val code: Int = 409,
    override val message: String = "Supplier email already exists!"
) : Failure