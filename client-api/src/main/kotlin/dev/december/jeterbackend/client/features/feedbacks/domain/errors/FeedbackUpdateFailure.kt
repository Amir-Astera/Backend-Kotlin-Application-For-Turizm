package dev.december.jeterbackend.client.features.feedbacks.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class FeedbackUpdateFailure(
    override val code: Int = 500,
    override val message: String = "Cannot update feedback!"
) : Failure
