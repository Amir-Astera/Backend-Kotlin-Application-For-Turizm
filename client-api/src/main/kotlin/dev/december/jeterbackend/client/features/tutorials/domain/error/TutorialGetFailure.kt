package dev.december.jeterbackend.client.features.tutorials.domain.error

import dev.december.jeterbackend.shared.core.errors.Failure

data class TutorialGetFailure(
    override val code: Int = 500,
    override val message: String = "Cannot get tutorial at this moment!"
) : Failure
