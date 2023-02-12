package dev.december.jeterbackend.shared.features.suppliers.data.repositories

import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface SupplierRepository : JpaRepository<SupplierEntity, String>,
    JpaSpecificationExecutor<SupplierEntity> {
    fun findAllByEnableStatusAndDeletedAtBetween(
        accountEnableStatus: AccountEnableStatus,
        from: LocalDateTime,
        to: LocalDateTime,
    ): List<SupplierEntity>

    fun findByEmail(email: String): SupplierEntity?
    fun findByPhone(phone: String): SupplierEntity?
    fun existsByEmail(email: String): Boolean
    fun findAllByActivityStatusAndEnableStatusAndStatus(
        accountActivityStatus: AccountActivityStatus,
        accountEnableStatus: AccountEnableStatus,
        status: SupplierStatus
    ): List<SupplierEntity>
}