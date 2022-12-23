package dev.december.jeterbackend.shared.features.suppliers.data.repositories.specifications

import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.FavoriteSupplierEntity
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
    }
}