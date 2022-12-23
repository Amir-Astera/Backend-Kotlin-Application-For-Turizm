package dev.december.jeterbackend.stream.signaler.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class AppointmentTooEarlyFailure(
    override val code: Int = 425,
    override val message: String = "Too early to start an appointment!"
) : Failure