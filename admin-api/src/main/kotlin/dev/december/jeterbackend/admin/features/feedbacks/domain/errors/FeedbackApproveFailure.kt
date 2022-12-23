package dev.december.jeterbackend.admin.features.feedbacks.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class FeedbackApproveFailure(
    override val code: Int = 500,
    override val message: String = "Cannot approve feedback!"
) : Failure