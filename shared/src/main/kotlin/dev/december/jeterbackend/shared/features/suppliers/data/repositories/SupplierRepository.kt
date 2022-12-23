package dev.december.jeterbackend.shared.features.suppliers.data.repositories

import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface SupplierRepository : JpaRepository<SupplierEntity, String>,
    JpaSpecificationExecutor<SupplierEntity> {

        fun findAllByActivityStatusAndEnableStatusAndStatus(
            accountActivityStatus: AccountActivityStatus,
            accountEnableStatus: AccountEnableStatus,
            status: SupplierStatus
        ): List<SupplierEntity>
}