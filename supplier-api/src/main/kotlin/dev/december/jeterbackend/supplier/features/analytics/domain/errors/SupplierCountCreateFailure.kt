package dev.december.jeterbackend.supplier.features.analytics.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure


data class SupplierCountCreateFailure(
    override val code: Int = 500,
    override val message: String = "Supplier Count Create Failure!"
) : Failure