package dev.december.jeterbackend.shared.features.promocodes.data.repositories

import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.promocodes.data.entities.PromocodeClientActivatedEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

@Repository
interface PromocodeClientActivatedRepository : JpaRepository<PromocodeClientActivatedEntity, String>,
    JpaSpecificationExecutor<PromocodeClientActivatedEntity> {
        fun findAllByClient(clientEntity: ClientEntity): List<PromocodeClientActivatedEntity>
}