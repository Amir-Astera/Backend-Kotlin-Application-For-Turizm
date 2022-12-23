package dev.december.jeterbackend.client.features.appointments.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class AppointmentConfirmationFailure(
    override val code: Int = 400,
    override val message: String = "Appointment confirmation failure!"
) : Failure