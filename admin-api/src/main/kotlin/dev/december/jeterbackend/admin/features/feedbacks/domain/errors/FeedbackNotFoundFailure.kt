package dev.december.jeterbackend.admin.features.feedbacks.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class FeedbackNotFoundFailure(
    override val code: Int = 404,
    override val message: String = "Feedback not found!"
) : Failure
