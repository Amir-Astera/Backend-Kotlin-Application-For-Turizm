package dev.december.jeterbackend.client.features.suppliers.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class SupplierListGetFailure(
    override val code: Int = 500,
    override val message: String = "Cannot get suppliers!"
) : Failure
