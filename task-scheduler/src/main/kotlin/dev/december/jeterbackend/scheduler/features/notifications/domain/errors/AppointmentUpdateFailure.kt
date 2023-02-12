package dev.december.jeterbackend.scheduler.features.notifications.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class AppointmentUpdateFailure(
    override val code: Int = 500,
    override val message: String = "Cannot update appointment!"
) : Failure