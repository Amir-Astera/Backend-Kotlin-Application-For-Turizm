package dev.december.jeterbackend.admin.features.articles.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.articles.domain.services.ArticleService
import dev.december.jeterbackend.shared.features.stories.domain.models.Stories
import org.springframework.stereotype.Component

@Component
class GetStoriesByIdUseCase (
    private val articleService: ArticleService
) : UseCase<GetStoriesByIdParams, Stories>{
    override suspend fun invoke(params: GetStoriesByIdParams): Data<Stories> {
        return articleService.getStoriesById(params.id)
    }
}

data class GetStoriesByIdParams(
    val id : String
)