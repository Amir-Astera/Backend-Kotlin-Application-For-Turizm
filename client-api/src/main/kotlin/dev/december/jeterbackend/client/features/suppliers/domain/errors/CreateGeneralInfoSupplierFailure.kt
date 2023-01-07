package dev.december.jeterbackend.client.features.suppliers.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class CreateGeneralInfoSupplierFailure(
    override val code: Int = 500,
    override val message: String = "Cannot add general info for supplier!"
) : Failure