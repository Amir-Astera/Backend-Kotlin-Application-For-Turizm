package dev.december.jeterbackend.admin.features.articles.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class StoriesAddSuppliersFailure(
    override val code: Int = 500,
    override val message: String = "Cannot add suppliers to stories!"
) : Failure