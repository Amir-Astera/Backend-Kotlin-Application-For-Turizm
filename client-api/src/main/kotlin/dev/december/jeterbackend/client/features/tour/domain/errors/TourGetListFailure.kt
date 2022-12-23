package dev.december.jeterbackend.client.features.tour.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class TourGetListFailure(
    override val code: Int = 500,
    override val message: String = "Cannot get tour at this moment!"
) : Failure