package dev.december.jeterbackend.admin.features.articles.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ArticleSupplierIsEmptyFailure(
    override val code: Int = 500,
    override val message: String = "Suppliers in article empty or null!"
) : Failure