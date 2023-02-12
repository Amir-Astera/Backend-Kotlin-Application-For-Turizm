package dev.december.jeterbackend.scheduler.features.supplier.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class UpdateSupplierActivityStatusFailure(
    override val code: Int = 500,
    override val message: String = "Can not update supplier activity status!"
): Failure