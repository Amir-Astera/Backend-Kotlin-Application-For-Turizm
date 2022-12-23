package dev.december.jeterbackend.shared.features.stories.data.repositories

import dev.december.jeterbackend.shared.features.stories.data.entities.StoriesEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface StoriesRepository : JpaRepository<StoriesEntity, String>, JpaSpecificationExecutor<StoriesEntity> {
}