package dev.december.jeterbackend.admin.features.faq.data.services

import dev.december.jeterbackend.admin.features.faq.domain.errors.*
import dev.december.jeterbackend.admin.features.faq.domain.services.FAQService
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.faq.data.entities.FAQEntity
import dev.december.jeterbackend.shared.features.faq.data.repositories.FAQRepository
import dev.december.jeterbackend.shared.features.faq.domain.models.FAQ
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class FAQServiceImpl(
    private val faqRepository: FAQRepository,
    private val dispatcher: CoroutineDispatcher,
) : FAQService {

    override suspend fun create(title: String, description: String, authority: PlatformRole): Data<String> {
        return try {
            withContext(dispatcher) {
                val faqEntity = faqRepository.save(
                    FAQEntity(
                        title = title,
                        description = description,
                        authority = authority
                    )
                )
                Data.Success(faqEntity.id)
            }
        } catch (e: Exception) {
            Data.Error(FAQCreateFailure())
        }
    }

    override suspend fun getAll(authority: PlatformRole): Data<List<FAQ>> {
        return try {
            withContext(dispatcher) {
                val entities = faqRepository.findAllByAuthorityOrderByUpdatedAtAsc(authority)
                val faqs: List<FAQ> = entities.map { articleEntity -> articleEntity.convert() }
                Data.Success(faqs)
            }
        } catch (e: Exception) {
            Data.Error(FAQGetListFailure())
        }
    }

    override suspend fun delete(id: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
                faqRepository.deleteById(id)
                Data.Success(Unit)
            }
        } catch (e: EmptyResultDataAccessException) {
            Data.Error(FAQNotFoundFailure())
        } catch (e: Exception) {
            Data.Error(FAQDeleteFailure())
        }
    }

    override suspend fun deleteList(ids: List<String>): Data<Unit> {
        return try {
            withContext(dispatcher) {
                faqRepository.deleteAllByIdIn(ids)
                Data.Success(Unit)
            }
        } catch (e: EmptyResultDataAccessException) {
            Data.Error(FAQNotFoundFailure())
        } catch (e: Exception) {
            Data.Error(FAQListDeleteFailure())
        }
    }

    override suspend fun update(id: String, title: String?, description: String?): Data<String> {
        return try {
            if (description == null && title == null) {
                return Data.Error(FAQUpdateFailure())
            }
            withContext(dispatcher) {
                val oldEntity = faqRepository.findByIdOrNull(id)
                    ?: return@withContext Data.Error(FAQNotFoundFailure())
                val newEntity = oldEntity.copy(
                    title = title ?: oldEntity.title,
                    description = description ?: oldEntity.description,
                    updatedAt = LocalDateTime.now()
                )
                faqRepository.save(newEntity)
                Data.Success(newEntity.id)
            }
        } catch (e: Exception) {
            Data.Error(FAQUpdateFailure())
        }
    }

}