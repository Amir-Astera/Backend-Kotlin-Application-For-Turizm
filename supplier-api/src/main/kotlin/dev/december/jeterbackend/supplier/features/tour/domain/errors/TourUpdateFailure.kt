package dev.december.jeterbackend.supplier.features.tour.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure


data class TourUpdateFailure(
    override val code: Int = 500,
    override val message: String = "Cannot update appointment!"
) : Failure