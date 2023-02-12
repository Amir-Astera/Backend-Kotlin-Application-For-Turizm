package dev.december.jeterbackend.shared.features.clients.data.repositories

import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ClientRepository : JpaRepository<ClientEntity, String>, JpaSpecificationExecutor<ClientEntity> {
    fun findByEmail(email: String): ClientEntity?
    fun findByPhone(phone: String): ClientEntity?

    fun findAllByEnableStatusAndDeletedAtBetween(
        accountEnableStatus: AccountEnableStatus,
        from: LocalDateTime,
        to: LocalDateTime,
    ): List<ClientEntity>

    fun existsByEmail(email: String): Boolean
}