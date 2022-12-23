package dev.december.jeterbackend.supplier.features.feedbacks.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class FeedbackCreateFailure(
    override val code: Int = 500,
    override val message: String = "Cannot create feedback!"
) : Failure
