package dev.december.jeterbackend.shared.features.admin.data.repositories

import dev.december.jeterbackend.shared.features.admin.data.entities.AdminEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface AdminRepository : JpaRepository<AdminEntity, String>, JpaSpecificationExecutor<AdminEntity> {
    fun findByEmail(email: String): AdminEntity?
    fun findByPhone(phone: String): AdminEntity?
}