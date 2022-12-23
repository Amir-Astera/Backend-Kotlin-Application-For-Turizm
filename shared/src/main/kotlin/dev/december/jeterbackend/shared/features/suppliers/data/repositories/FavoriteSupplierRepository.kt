package dev.december.jeterbackend.shared.features.suppliers.data.repositories

import dev.december.jeterbackend.shared.features.suppliers.data.entiies.FavoriteSupplierEntity
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

@Repository
interface FavoriteSupplierRepository : JpaRepository<FavoriteSupplierEntity, String>,
    JpaSpecificationExecutor<FavoriteSupplierEntity> {

    fun findAllByClientIdAndSupplierIsIn(clientId: String, supplierIds: List<SupplierEntity>): List<FavoriteSupplierEntity>

    fun existsByClientIdAndSupplierId(clientId: String, supplierId: String): Boolean

    fun existsBySupplierId(supplierId: String): Boolean
}