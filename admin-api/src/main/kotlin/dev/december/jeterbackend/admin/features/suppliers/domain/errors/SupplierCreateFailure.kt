package dev.bytepride.truprobackend.admin.features.suppliers.domain.errors

import dev.bytepride.truprobackend.core.errors.Failure

data class SupplierCreateFailure(
    override val code: Int = 500,
    override val message: String = "Cannot create supplier"
) : Failure