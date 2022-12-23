package dev.december.jeterbackend.shared.features.faq.data.repositories

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.features.faq.data.entities.FAQEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface FAQRepository : CrudRepository<FAQEntity, String> {
    fun findAllByAuthorityOrderByUpdatedAtAsc(authority: PlatformRole): List<FAQEntity>

    @Transactional
    fun deleteAllByIdIn(ids: List<String>)
}