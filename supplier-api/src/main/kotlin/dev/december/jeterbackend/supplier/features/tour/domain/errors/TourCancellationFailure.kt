package dev.december.jeterbackend.supplier.features.tour.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class TourCancellationFailure(
    override val code: Int = 400,
    override val message: String = "Tour cancellation failure!"
) : Failure