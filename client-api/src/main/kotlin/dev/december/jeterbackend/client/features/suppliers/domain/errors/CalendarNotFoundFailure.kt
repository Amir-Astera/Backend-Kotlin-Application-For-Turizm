package dev.december.jeterbackend.client.features.suppliers.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class CalendarNotFoundFailure(
    override val code: Int = 404,
    override val message: String = "Calendar not found!"
) : Failure