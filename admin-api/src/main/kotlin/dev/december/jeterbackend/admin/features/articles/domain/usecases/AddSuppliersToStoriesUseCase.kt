package dev.december.jeterbackend.admin.features.articles.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.articles.domain.services.ArticleService
import org.springframework.stereotype.Component


@Component
class AddSuppliersToStoriesUseCase(
    private val articleService: ArticleService
) : UseCase<AddSuppliersStoriesParams, Unit> {
    override suspend fun invoke(params: AddSuppliersStoriesParams): Data<Unit> {
        return articleService.addSuppliersToStories(params.id, params.supplierIds)
    }
}

data class AddSuppliersStoriesParams(
    val id: String,
    val supplierIds: List<String>
)