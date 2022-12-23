package dev.bytepride.truprobackend.admin.features.articles.data.services

import dev.december.jeterbackend.admin.features.articles.domain.errors.*
import dev.december.jeterbackend.admin.features.articles.domain.services.ArticleService
import dev.december.jeterbackend.admin.features.articles.presentation.dto.CreateStoriesData
import dev.december.jeterbackend.admin.features.files.domain.services.FileService
import dev.december.jeterbackend.admin.features.suppliers.domain.errors.SupplierNotFoundFailure
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.articles.data.entities.ArticleEntity
import dev.december.jeterbackend.shared.features.articles.data.entities.extentions.article
import dev.december.jeterbackend.shared.features.articles.data.repositories.ArticleRepository
import dev.december.jeterbackend.shared.features.articles.data.repositories.specifications.ArticleSpecification
import dev.december.jeterbackend.shared.features.articles.domain.models.Article
import dev.december.jeterbackend.shared.features.articles.domain.models.ArticleProfession
import dev.december.jeterbackend.shared.features.articles.domain.models.ArticleSortDirection
import dev.december.jeterbackend.shared.features.articles.domain.models.ArticleSortField
import dev.december.jeterbackend.shared.features.files.data.repositories.FileRepository
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.stories.data.entities.StoriesEntity
import dev.december.jeterbackend.shared.features.stories.data.entities.extentions.stories
import dev.december.jeterbackend.shared.features.stories.data.repositories.StoriesRepository
import dev.december.jeterbackend.shared.features.stories.domain.models.Stories
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.SupplierRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ArticleServiceImpl(
    private val articleRepository: ArticleRepository,
    private val fileRepository: FileRepository,
//    private val professionRepository: ProfessionRepository,
    private val supplierRepository: SupplierRepository,
    private val dispatcher: CoroutineDispatcher,
    private val fileService: FileService,
    private val storiesRepository: StoriesRepository,
) : ArticleService {

    override suspend fun create(
        title: String,
        priority: Int,
        files: List<File>?,
        storyDtos: List<CreateStoriesData>?,
    ): Data<String> {
        return try {
            withContext(dispatcher) {
                val fileEntities = files?.let { fileRepository.findAllById(it.map { file -> file.id }) }

                val articleEntity = articleRepository.save(
                    ArticleEntity(
                        title = title,
                        priority = priority,
                        files = fileEntities?.toSet() ?: emptySet(),
                    )
                )
                val storiesEntities = mutableSetOf<StoriesEntity>()
                storyDtos?.forEach { story ->
                    val storyFiles = story.files?.let { fileRepository.findAllById(it.map { file -> file.id }) }
                    val storySuppliers: MutableSet<SupplierEntity> = mutableSetOf()
                    story.supplierIds?.forEach { it ->
                        storySuppliers.add(
                            supplierRepository.findByIdOrNull(it)
                                ?: return@withContext Data.Error(SupplierNotFoundFailure())
                        )
                    }
                    storiesEntities.add(
                        storiesRepository.save(
                            StoriesEntity(
                                title = story.title,
                                priority = story.priority,
                                html_content = story.html_content,
                                files = storyFiles?.toSet() ?: emptySet(),
                                suppliers = storySuppliers,
                            )
                        )
                    )
                }
                articleRepository.save(articleEntity.copy(stories = storiesEntities))

                Data.Success(articleEntity.id)
            }
        } catch (e: DataIntegrityViolationException) {
            Data.Error(ArticleAlreadyExist())
        } catch (e: Exception) {
            Data.Error(ArticleCreateFailure())
        }
    }

    override suspend fun getAll(
        sortField: ArticleSortField,
        sortDirection: ArticleSortDirection,
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?
    ): Data<Page<Article>> {
        return try {
            withContext(dispatcher) {
                val sortParams: Sort = sortField.getSortField(sortDirection)

                val pageable = PageRequest.of(page, size, sortParams)

                val specifications: Specification<ArticleEntity> =
                    Specification.where(
                        ArticleSpecification.isGreaterThanCreatedAt(createdFrom)
                            ?.and(ArticleSpecification.isLessThanCreatedAt(createdTo))
                    )


                val entities: Page<ArticleEntity> = articleRepository.findAll(specifications, pageable)
                val articles: Page<Article> = entities.map { articleEntity -> articleEntity.article() }
                Data.Success(articles)
            }
        } catch (e: Exception) {
            Data.Error(ArticleGetListFailure())
        }
    }

    override suspend fun get(id: String): Data<Article> {
        return try {
            withContext(dispatcher) {
                val article = articleRepository.findByIdOrNull(id)
                    ?: return@withContext Data.Error(ArticleNotFoundFailure())
                Data.Success(article.article())
            }
        } catch (e: Exception) {
            Data.Error(ArticleGetFailure())
        }
    }

    override suspend fun delete(id: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
                articleRepository.findByIdOrNull(id)?.files?.forEach {
                    fileService.deleteFile(it.id, it.directory, it.format)
                }
                articleRepository.deleteById(id)
                Data.Success(Unit)
            }
        } catch (e: EmptyResultDataAccessException) {
            Data.Error(ArticleNotFoundFailure())
        } catch (e: Exception) {
            Data.Error(ArticleDeleteFailure())
        }
    }

    override suspend fun deleteList(ids: List<String>): Data<Unit> {
        return try {
            withContext(dispatcher) {
                ids.map {id ->
                    articleRepository.findByIdOrNull(id)?.files?.forEach {
                        fileService.deleteFile(it.id, it.directory, it.format)
                    }
                }
                articleRepository.deleteAllById(ids)

                Data.Success(Unit)
            }
        } catch (e: EmptyResultDataAccessException) {
        Data.Error(ArticleNotFoundFailure())
        } catch (e: Exception) {
            Data.Error(ArticleListDeleteFailure())
        }
    }

    override suspend fun update(
        id: String,
        title: String?,
        priority: Int?,
        files: List<File>?
    ): Data<String> {
        return try {
            withContext(dispatcher) {
                val oldEntity = articleRepository.findByIdOrNull(id)
                    ?: return@withContext Data.Error(ArticleNotFoundFailure())
                val fileEntities = files?.let { fileRepository.findAllById(it.map { file -> file.id }) }
                articleRepository.save(oldEntity.copy(
                    title = title ?: oldEntity.title,
                    priority = priority ?: oldEntity.priority,
                    files = fileEntities?.toSet() ?: oldEntity.files,
                    updatedAt = LocalDateTime.now(),
                ))
                Data.Success(oldEntity.id)
            }
        } catch (e: Exception) {
            Data.Error(ArticleUpdateFailure())
        }
    }

    override suspend fun addSuppliersToStories(id: String, supplierIds: List<String>): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val storiesEntity = storiesRepository.findByIdOrNull(id)
                    ?: return@withContext Data.Error(StoriesNotFoundFailure())
                var suppliers: Set<SupplierEntity> = emptySet()
                supplierIds.forEach { it -> suppliers = suppliers.plus(supplierRepository.findByIdOrNull(it)
                    ?: return@withContext Data.Error(SupplierNotFoundFailure())) }
                storiesRepository.save(storiesEntity.copy(
                    suppliers = storiesEntity.suppliers?.plus(suppliers) ?: suppliers,
                    updatedAt = LocalDateTime.now()
                ))
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(StoriesAddSuppliersFailure())
        }
    }

    override suspend fun deleteSuppliersFromStories(id: String, supplierIds: List<String>): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val storiesEntity = storiesRepository.findByIdOrNull(id)
                    ?: return@withContext Data.Error(StoriesNotFoundFailure())
                var suppliers: Set<SupplierEntity> = emptySet()
                supplierIds.forEach { it -> suppliers = suppliers.plus(supplierRepository.findByIdOrNull(it)
                    ?: return@withContext Data.Error(SupplierNotFoundFailure())) }
                var storiesSuppliers = storiesEntity.suppliers
                if (storiesSuppliers != null && storiesSuppliers.isNotEmpty()) {
                    if (storiesSuppliers.containsAll(suppliers)){
                        storiesSuppliers = storiesSuppliers.minus(suppliers)
                    } else return@withContext Data.Error(StoriesSupplierMatchFailure())
                } else return@withContext Data.Error(ArticleSupplierIsEmptyFailure())
                storiesRepository.save(storiesEntity.copy(
                    suppliers = storiesSuppliers,
                    updatedAt = LocalDateTime.now()
                ))
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(StoriesDeleteSuppliersFailure())
        }
    }

    override suspend fun getStoriesById(id: String): Data<Stories> {
        return try {
            withContext(dispatcher) {
                val storiesEntity = storiesRepository.findByIdOrNull(id) ?: return@withContext Data.Error(SupplierNotFoundFailure())
                Data.Success(storiesEntity.stories())
            }
        } catch (e: Exception) {
            Data.Error(StoriesGetFailure())
        }
    }

    override suspend fun patchStories(articleId: String, storyDtos: List<CreateStoriesData>): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val articleEntity = articleRepository.findByIdOrNull(articleId)
                    ?: return@withContext Data.Error(ArticleNotFoundFailure())
                val oldStories = articleEntity.stories
                val storiesEntities = mutableSetOf<StoriesEntity>()
                storyDtos.forEach { story ->
                    val storyFiles = story.files?.let { fileRepository.findAllById(it.map { file -> file.id }) }
                    val storySuppliers: MutableSet<SupplierEntity> = mutableSetOf()
                    story.supplierIds?.forEach { it ->
                        storySuppliers.add(
                            supplierRepository.findByIdOrNull(it)
                                ?: return@withContext Data.Error(SupplierNotFoundFailure())
                        )
                    }
                    storiesEntities.add(
                        storiesRepository.save(
                            StoriesEntity(
                                title = story.title,
                                priority = story.priority,
                                html_content = story.html_content,
                                files = storyFiles?.toSet() ?: emptySet(),
                                suppliers = storySuppliers,
                            )
                        )
                    )
                }

                articleRepository.save(articleEntity.copy(
                    stories = storiesEntities,
                    updatedAt = LocalDateTime.now()
                ))
                if (oldStories.isNotEmpty()){
                    storiesRepository.deleteAll(oldStories)
                }
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(StoriesPatchFailure())
        }
    }

    override suspend fun getArticlesByProfession(
        professionId: String,
        sortField: ArticleSortField,
        sortDirection: ArticleSortDirection,
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?
    ): Data<Unit> {//Page<ArticleProfession>
        return try {
            withContext(dispatcher) {
//                val professionEntity = professionRepository.findByIdOrNull(professionId) ?:
//                return@withContext Data.Error(ProfessionNotFoundFailure())
//
//                val sortParams: Sort = sortField.getSortField(sortDirection)
//
//                val pageable = PageRequest.of(page, size, sortParams)
//
//                val specifications: Specification<ArticleEntity> =
//                    Specification.where(
//                        ArticleSpecification.isGreaterThanCreatedAt(createdFrom)
//                            ?.and(ArticleSpecification.isLessThanCreatedAt(createdTo))
//                    ).and(ArticleSpecification.hasProfession(professionEntity))
//
//                val articleEntities = articleRepository.findAll(specifications, pageable)
//
//                val articles: Page<ArticleProfession> = articleEntities.map { articleEntity ->
//                    articleEntity.convert(
//                        mapOf(
//                            "files" to articleEntity.files.map<FileEntity, File>{ it.convert() }
//                        )
//                    )
//                }

                Data.Success(Unit)//articles
            }
        } catch (e: Exception) {
            Data.Error(ArticleNotFoundFailure())
        }
    }
}



