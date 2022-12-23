package dev.december.jeterbackend.admin.features.articles.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.articles.domain.services.ArticleService
import dev.december.jeterbackend.admin.features.articles.presentation.dto.CreateStoriesData
import org.springframework.stereotype.Component


@Component
class PatchStoriesUseCase(
    private val articleService: ArticleService
) : UseCase<PatchStoriesParams, Unit> {
    override suspend fun invoke(params: PatchStoriesParams): Data<Unit> {
        return articleService.patchStories(params.id, params.stories)
    }
}

data class PatchStoriesParams(
    val id: String,
    val stories: List<CreateStoriesData>
)