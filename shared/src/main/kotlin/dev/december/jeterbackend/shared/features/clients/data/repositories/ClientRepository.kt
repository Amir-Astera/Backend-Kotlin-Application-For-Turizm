package dev.december.jeterbackend.shared.features.clients.data.repositories

import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<ClientEntity, String>, JpaSpecificationExecutor<ClientEntity> {
}