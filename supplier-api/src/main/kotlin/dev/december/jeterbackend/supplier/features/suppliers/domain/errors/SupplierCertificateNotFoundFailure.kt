package dev.december.jeterbackend.supplier.features.suppliers.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class SupplierCertificateNotFoundFailure(
    override val code: Int = 404,
    override val message: String = "Supplier certificate not found!"
): Failure