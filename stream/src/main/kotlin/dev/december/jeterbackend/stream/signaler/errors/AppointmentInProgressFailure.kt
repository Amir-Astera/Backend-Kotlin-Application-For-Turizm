package dev.december.jeterbackend.stream.signaler.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class AppointmentInProgressFailure(
    override val code: Int = 400,
    override val message: String = "You cannot join appointment because it has already started!"
) : Failure