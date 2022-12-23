package dev.december.jeterbackend.admin.features.articles.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.articles.domain.services.ArticleService
import dev.december.jeterbackend.shared.features.articles.domain.models.Article
import dev.december.jeterbackend.shared.features.articles.domain.models.ArticleSortDirection
import dev.december.jeterbackend.shared.features.articles.domain.models.ArticleSortField
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import java.time.LocalDateTime


@Component
class GetArticleListUseCase(
    private val articleService: ArticleService
) : UseCase<GetArticleListParams, Page<Article>> {
  override suspend fun invoke(params: GetArticleListParams): Data<Page<Article>> {
    return articleService.getAll(
        params.sortField,
        params.sortDirection,
        params.page,
        params.size,
        params.createdFrom,
        params.createdTo
    )
  }
}

data class GetArticleListParams(
    val sortField: ArticleSortField,
    val sortDirection: ArticleSortDirection,
    val page: Int,
    val size: Int,
    val createdFrom: LocalDateTime?,
    val createdTo: LocalDateTime?
)