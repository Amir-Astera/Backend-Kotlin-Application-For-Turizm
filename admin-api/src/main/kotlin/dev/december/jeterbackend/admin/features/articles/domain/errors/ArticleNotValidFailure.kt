package dev.december.jeterbackend.admin.features.articles.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ArticleNotValidFailure(
    override val code: Int = 400,
    override val message: String = "Invalid request - name and description are blank!"
) : Failure