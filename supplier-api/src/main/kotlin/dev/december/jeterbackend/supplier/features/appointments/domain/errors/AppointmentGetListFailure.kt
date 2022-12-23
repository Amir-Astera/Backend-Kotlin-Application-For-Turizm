package dev.december.jeterbackend.supplier.features.appointments.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class AppointmentGetListFailure(
    override val code: Int = 500,
    override val message: String = "Cannot get appointment at this moment!"
) : Failure