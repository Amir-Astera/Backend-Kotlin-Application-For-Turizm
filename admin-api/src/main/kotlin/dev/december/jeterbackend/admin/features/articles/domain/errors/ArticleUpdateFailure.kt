package dev.december.jeterbackend.admin.features.articles.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ArticleUpdateFailure(
    override val code: Int = 500,
    override val message: String = "Cannot update article!"
) : Failure