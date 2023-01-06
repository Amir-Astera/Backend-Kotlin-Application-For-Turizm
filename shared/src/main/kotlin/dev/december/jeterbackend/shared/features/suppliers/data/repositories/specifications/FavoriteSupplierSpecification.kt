package dev.december.jeterbackend.shared.features.suppliers.data.repositories.specifications

import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.FavoriteSupplierEntity
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierStatus
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component

@Component
class FavoriteSupplierSpecification {
    companion object {
        fun clientJoinFilter(clientId: String): Specification<FavoriteSupplierEntity> {
            return Specification<FavoriteSupplierEntity> { root, _, criteriaBuilder ->
                val clientJoin = root.join<FavoriteSupplierEntity, ClientEntity>("client")
                criteriaBuilder.equal(clientJoin.get<String>("id"), clientId)
            }
        }
        fun approvedFilter(): Specification<FavoriteSupplierEntity> {
            return Specification<FavoriteSupplierEntity> { root, _, criteriaBuilder ->
                val supplierJoin = root.join<FavoriteSupplierEntity, SupplierEntity>("supplier")
                criteriaBuilder.equal(supplierJoin.get<String>("status"), SupplierStatus.APPROVED)
            }
        }
        fun enabledFilter(): Specification<FavoriteSupplierEntity> {
            return Specification<FavoriteSupplierEntity> { root, _, criteriaBuilder ->
                val supplierJoin = root.join<FavoriteSupplierEntity, SupplierEntity>("supplier")
                criteriaBuilder.equal(supplierJoin.get<String>("enableStatus"), AccountEnableStatus.ENABLED)
            }
        }
    }
}