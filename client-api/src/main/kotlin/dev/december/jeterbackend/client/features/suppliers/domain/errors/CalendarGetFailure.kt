package dev.december.jeterbackend.client.features.suppliers.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class CalendarGetFailure(
    override val code: Int = 500,
    override val message: String = "Can not get calendar at this moment"
) : Failure