package dev.december.jeterbackend.supplier.features.feedbacks.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class FeedbackDeleteFailure(
    override val code: Int = 500,
    override val message: String = "Cannot delete feedback!"
) : Failure