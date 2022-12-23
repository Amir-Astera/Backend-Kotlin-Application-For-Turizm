package dev.december.jeterbackend.supplier.features.suppliers.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class SupplierDisabledFailure(
    val isDisabled: Boolean,
    override val code: Int = 401,
    override val message: String = "Supplier disabled!"
) : Failure
