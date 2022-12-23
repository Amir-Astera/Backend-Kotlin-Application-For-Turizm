package dev.december.jeterbackend.shared.features.suppliers.data.repositories

import dev.december.jeterbackend.shared.features.suppliers.data.entiies.EducationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EducationRepository: JpaRepository<EducationEntity, String> {
    fun findBySupplierId(supplierId: String): EducationEntity?
}