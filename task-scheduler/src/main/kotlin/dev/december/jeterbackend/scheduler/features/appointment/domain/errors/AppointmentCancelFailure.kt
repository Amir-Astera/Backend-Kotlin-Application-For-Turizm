package dev.december.jeterbackend.scheduler.features.appointment.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class AppointmentCancelFailure(
    override val code: Int = 500,
    override val message: String = "Can not cancel appointment"
): Failure