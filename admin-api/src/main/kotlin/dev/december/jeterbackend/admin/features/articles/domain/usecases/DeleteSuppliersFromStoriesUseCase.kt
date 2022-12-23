package dev.december.jeterbackend.admin.features.articles.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.articles.domain.services.ArticleService
import org.springframework.stereotype.Component


@Component
class DeleteSuppliersFromStoriesUseCase(
    private val articleService: ArticleService
) : UseCase<DeleteSuppliersStoriesParams, Unit> {
    override suspend fun invoke(params: DeleteSuppliersStoriesParams): Data<Unit> {
        return articleService.deleteSuppliersFromStories(params.id, params.supplierIds)
    }
}

data class DeleteSuppliersStoriesParams(
    val id: String,
    val supplierIds: List<String>
)