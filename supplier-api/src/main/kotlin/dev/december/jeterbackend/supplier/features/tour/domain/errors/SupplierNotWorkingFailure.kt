package dev.december.jeterbackend.supplier.features.tour.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure


data class SupplierNotWorkingFailure(
    override val code: Int = 400,
    override val message: String = "Supplier is not working at this date"
) : Failure