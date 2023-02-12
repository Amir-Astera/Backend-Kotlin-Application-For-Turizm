package dev.december.jeterbackend.supplier.features.suppliers.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class AppointmentsExistAtGivenDateFailure(
    override val code: Int = 400,
    override val message: String = "Appointments exist on the given date!"
) : Failure