package dev.december.jeterbackend.supplier.features.faq.data.services

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.faq.data.repositories.FAQRepository
import dev.december.jeterbackend.shared.features.faq.domain.models.FAQ
import dev.december.jeterbackend.supplier.features.faq.domain.errors.FAQGetListFailure
import dev.december.jeterbackend.supplier.features.faq.domain.services.FAQService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class FAQServiceImpl(
    private val faqRepository: FAQRepository,
    private val dispatcher: CoroutineDispatcher,
) : FAQService {

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

}