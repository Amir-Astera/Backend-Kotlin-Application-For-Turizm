package dev.december.jeterbackend.client.features.suppliers.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class SupplierEducationAddFailure(
    override val code: Int = 500,
    override val message: String = "Can not add education"
) : Failure