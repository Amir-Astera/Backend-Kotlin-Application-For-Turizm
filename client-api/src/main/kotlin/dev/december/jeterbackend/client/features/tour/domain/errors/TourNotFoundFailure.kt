package dev.december.jeterbackend.client.features.tour.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class TourNotFoundFailure(
    override val code: Int = 404,
    override val message: String = "Tour not found!"
) : Failure