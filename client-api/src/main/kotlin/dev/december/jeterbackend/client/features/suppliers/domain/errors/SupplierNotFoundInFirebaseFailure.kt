package dev.december.jeterbackend.client.features.suppliers.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class SupplierNotFoundInFirebaseFailure(
    override val code: Int = 400,
    override val message: String = "No supplier with this number found!"
) : Failure