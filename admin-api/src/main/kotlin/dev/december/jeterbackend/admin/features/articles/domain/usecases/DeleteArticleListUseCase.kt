package dev.december.jeterbackend.admin.features.articles.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.articles.domain.services.ArticleService
import org.springframework.stereotype.Component

@Component
class DeleteArticleListUseCase(
    private val articleService: ArticleService
): UseCase<DeleteArticleListParams, Unit> {
    override suspend fun invoke(params: DeleteArticleListParams): Data<Unit> {
        return articleService.deleteList(params.ids)
    }

}

data class DeleteArticleListParams(
    var ids: List<String>
)