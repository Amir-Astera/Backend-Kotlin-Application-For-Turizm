package dev.december.jeterbackend.shared.features.tutorials.data.repositories

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.features.tutorials.data.entities.TutorialEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TutorialRepository : CrudRepository<TutorialEntity, String> {
    fun findAllByOrderByPriorityAsc() : List<TutorialEntity>

    fun findAllByAuthorityOrderByPriorityAsc(authority: PlatformRole): List<TutorialEntity>
}
