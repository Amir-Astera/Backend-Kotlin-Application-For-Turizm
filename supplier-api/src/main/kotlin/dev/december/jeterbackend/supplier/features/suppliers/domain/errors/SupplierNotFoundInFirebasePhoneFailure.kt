package dev.december.jeterbackend.supplier.features.suppliers.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class SupplierNotFoundInFirebasePhoneFailure(
    override val code: Int = 404,
    override val message: String = "No supplier with this number found!"
) : Failure