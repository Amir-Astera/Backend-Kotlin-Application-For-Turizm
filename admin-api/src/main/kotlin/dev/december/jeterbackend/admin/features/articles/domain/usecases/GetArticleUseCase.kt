package dev.december.jeterbackend.admin.features.articles.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.articles.domain.services.ArticleService
import dev.december.jeterbackend.shared.features.articles.domain.models.Article
import org.springframework.stereotype.Component

@Component
class GetArticleUseCase (
    private val articleService: ArticleService
) : UseCase<GetArticleParams, Article>{
    override suspend fun invoke(params: GetArticleParams): Data<Article> {
        return articleService.get(params.id)
    }
}

data class GetArticleParams(
    val id : String
)