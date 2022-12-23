package dev.december.jeterbackend.admin.features.articles.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ArticleGetFailure(
    override val code : Int = 500,
    override val message : String = "Cannot get article at this moment!"
) : Failure
