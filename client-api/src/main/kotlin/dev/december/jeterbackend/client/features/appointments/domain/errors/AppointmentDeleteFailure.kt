package dev.december.jeterbackend.client.features.appointments.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class AppointmentDeleteFailure(
    override val code: Int = 500,
    override val message: String = "Cannot delete appointment!"
) : Failure