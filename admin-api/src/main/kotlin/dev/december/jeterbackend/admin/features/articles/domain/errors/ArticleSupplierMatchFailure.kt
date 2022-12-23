package dev.december.jeterbackend.admin.features.articles.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ArticleSupplierMatchFailure(
    override val code: Int = 500,
    override val message: String = "Suppliers you chose doesnt match article's!"
) : Failure