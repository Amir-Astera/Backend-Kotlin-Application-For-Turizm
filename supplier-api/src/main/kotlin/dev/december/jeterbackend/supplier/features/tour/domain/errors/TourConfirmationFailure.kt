package dev.december.jeterbackend.supplier.features.tours.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure


data class TourConfirmationFailure(
    override val code: Int = 400,
    override val message: String = "Tour confirmation failure!"
) : Failure