package dev.december.jeterbackend.client.features.files.domain.error

import dev.december.jeterbackend.shared.core.errors.Failure

data class FileStoreFailure(
    override val code: Int = 500,
    override val message: String = "Cannot store file!"
) : Failure