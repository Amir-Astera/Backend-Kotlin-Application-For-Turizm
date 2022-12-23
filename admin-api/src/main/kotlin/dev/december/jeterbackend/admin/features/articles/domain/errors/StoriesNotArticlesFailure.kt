package dev.december.jeterbackend.admin.features.articles.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class StoriesNotArticlesFailure(
    override val code: Int = 404,
    override val message: String = "Stories doesnt belong to this article!"
) : Failure