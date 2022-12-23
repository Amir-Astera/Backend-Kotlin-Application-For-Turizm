package dev.december.jeterbackend.admin.features.articles.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ArticleGetListFailure(
    override val code: Int = 500,
    override val message: String = "Cannot get articles at this moment!"
) : Failure
