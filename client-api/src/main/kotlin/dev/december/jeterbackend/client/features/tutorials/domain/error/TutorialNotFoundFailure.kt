package dev.december.jeterbackend.client.features.tutorials.domain.error

import dev.december.jeterbackend.shared.core.errors.Failure

data class TutorialNotFoundFailure(
    override val code: Int = 404,
    override val message: String = "Tutorial not found!"
) : Failure