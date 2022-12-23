package dev.december.jeterbackend.admin.features.articles.domain.usecases

import dev.december.jeterbackend.admin.features.articles.domain.services.ArticleService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.files.domain.models.File
import org.springframework.stereotype.Component

@Component
class UpdateArticleUseCase(
    private val articleService: ArticleService
) : UseCase<UpdateArticleParams, String> {
    override suspend fun invoke(params: UpdateArticleParams): Data<String> {
        return articleService.update(params.id, params.title, params.priority, params.files)
    }
}

data class UpdateArticleParams(
    val id: String,
    val title: String?,
    val priority: Int?,
    val files : List<File>?,
)