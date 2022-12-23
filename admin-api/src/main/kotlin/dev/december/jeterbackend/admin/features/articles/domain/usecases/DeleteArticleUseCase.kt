package dev.december.jeterbackend.admin.features.articles.domain.usecases

import dev.december.jeterbackend.admin.features.articles.domain.services.ArticleService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class DeleteArticleUseCase (
    private val articleService: ArticleService
) : UseCase<DeleteArticleParams, Unit> {
    override suspend fun invoke(params: DeleteArticleParams): Data<Unit> {
        return articleService.delete(params.id)
    }
}

data class DeleteArticleParams(
    val id: String
)