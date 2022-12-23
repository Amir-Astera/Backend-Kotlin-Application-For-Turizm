package dev.december.jeterbackend.shared.features.promocodes.data.repositories.specifications

import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.promocodes.data.entities.PromocodeClientActivatedEntity
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.FavoriteSupplierEntity
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component

@Component
class PromocodeClientActivatedSpecification {
    companion object {
        fun clientJoinFilter(clientId: String): Specification<PromocodeClientActivatedEntity> {
            return Specification<PromocodeClientActivatedEntity> { root, _, criteriaBuilder ->
                val clientJoin = root.join<FavoriteSupplierEntity, ClientEntity>("client")
                criteriaBuilder.equal(clientJoin.get<String>("id"), clientId)
            }
        }
    }
}