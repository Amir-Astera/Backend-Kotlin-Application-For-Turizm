package dev.december.jeterbackend.supplier.features.appointments.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class AppointmentNotFoundFailure(
    override val code: Int = 404,
    override val message: String = "Appointment not found!"
) : Failure