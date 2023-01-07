package dev.december.jeterbackend.client.features.suppliers.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class SupplierNotFoundFailure(
    override val code: Int = 404,
    override val message: String = "Supplier not found!"
) : Failure
