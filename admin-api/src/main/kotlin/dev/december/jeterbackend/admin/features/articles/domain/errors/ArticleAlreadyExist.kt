package dev.december.jeterbackend.admin.features.articles.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ArticleAlreadyExist(
    override val code: Int = 409,
    override val message: String = "Article with this title already exists"
) : Failure