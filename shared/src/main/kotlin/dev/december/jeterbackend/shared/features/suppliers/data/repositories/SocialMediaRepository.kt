package dev.december.jeterbackend.shared.features.suppliers.data.repositories

import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SocialMediaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SocialMediaRepository: JpaRepository<SocialMediaEntity, String> {
    fun findBySupplierId(supplierId: String): SocialMediaEntity?
}