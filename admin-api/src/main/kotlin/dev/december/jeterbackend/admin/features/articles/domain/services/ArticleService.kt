package dev.december.jeterbackend.admin.features.articles.domain.services

import dev.december.jeterbackend.admin.features.articles.presentation.dto.CreateStoriesData
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.articles.domain.models.Article
import dev.december.jeterbackend.shared.features.articles.domain.models.ArticleProfession
import dev.december.jeterbackend.shared.features.articles.domain.models.ArticleSortDirection
import dev.december.jeterbackend.shared.features.articles.domain.models.ArticleSortField
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.stories.domain.models.Stories
import org.springframework.data.domain.Page
import java.time.LocalDateTime

interface ArticleService {
    suspend fun create(
        title: String,
        priority: Int,
        files: List<File>?,
        storyDtos: List<CreateStoriesData>?
    ): Data<String>
    suspend fun get(id : String): Data<Article>
    suspend fun getAll(
        sortField: ArticleSortField,
        sortDirection: ArticleSortDirection,
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?
    ): Data<Page<Article>>
    suspend fun delete(id: String): Data<Unit>
    suspend fun deleteList(ids: List<String>): Data<Unit>
    suspend fun update(id: String,
                       title: String?,
                       priority: Int?,
                       files: List<File>?
    ): Data<String>
    suspend fun addSuppliersToStories(
        id: String,
        supplierIds: List<String>
    ): Data<Unit>
    suspend fun deleteSuppliersFromStories(
        id: String,
        supplierIds: List<String>
    ): Data<Unit>
    suspend fun getArticlesByProfession(
        professionId: String,
        sortField: ArticleSortField,
        sortDirection: ArticleSortDirection,
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?
    ): Data<Unit>//Page<ArticleProfession>
    suspend fun getStoriesById(
        id: String
    ): Data<Stories>
    suspend fun patchStories(
        articleId: String,
        storyDtos: List<CreateStoriesData>
    ): Data<Unit>
}