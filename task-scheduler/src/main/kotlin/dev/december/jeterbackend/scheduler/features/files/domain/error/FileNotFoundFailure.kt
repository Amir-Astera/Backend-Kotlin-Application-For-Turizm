package dev.december.jeterbackend.scheduler.features.files.domain.error

import dev.december.jeterbackend.shared.core.errors.Failure

data class FileNotFoundFailure(
    override val code: Int = 500,
    override val message: String = "Cannot store file!"
) : Failure