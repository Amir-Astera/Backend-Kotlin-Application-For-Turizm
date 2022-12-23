package dev.december.jeterbackend.admin.features.articles.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.articles.domain.services.ArticleService
import dev.december.jeterbackend.admin.features.articles.presentation.dto.CreateStoriesData
import dev.december.jeterbackend.shared.features.files.domain.models.File
import org.springframework.stereotype.Component


@Component
class CreateArticleUseCase(
    private val articleService: ArticleService
) : UseCase<CreateArticleParams, String> {
    override suspend fun invoke(params: CreateArticleParams): Data<String> {
        return articleService.create(params.title, params.priority, params.files, params.stories)
    }
}

data class CreateArticleParams(
    val title: String,
    val priority: Int,
    val files: List<File>?,
    val stories: List<CreateStoriesData>?
)