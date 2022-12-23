package dev.december.jeterbackend.supplier.features.appointments.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class AppointmentCancellationFailure(
    override val code: Int = 400,
    override val message: String = "Appointment cancellation failure!"
) : Failure