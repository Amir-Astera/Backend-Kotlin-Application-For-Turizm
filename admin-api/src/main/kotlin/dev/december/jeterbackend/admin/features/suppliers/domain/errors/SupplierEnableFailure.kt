package dev.december.jeterbackend.admin.features.suppliers.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class SupplierEnableFailure(
    override val code: Int = 500,
    override val message: String = "Cannot enable supplier!"
) : Failure