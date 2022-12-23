package dev.december.jeterbackend.shared.features.articles.data.repositories

import dev.december.jeterbackend.shared.features.articles.data.entities.ArticleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<ArticleEntity, String>, JpaSpecificationExecutor<ArticleEntity> {
    fun findAllByOrderByPriorityAsc(): List<ArticleEntity>
}