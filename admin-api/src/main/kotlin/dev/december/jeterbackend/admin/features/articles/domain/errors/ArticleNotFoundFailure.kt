package dev.december.jeterbackend.admin.features.articles.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ArticleNotFoundFailure(
    override val code: Int = 404,
    override val message: String = "Article not found!"
) : Failure