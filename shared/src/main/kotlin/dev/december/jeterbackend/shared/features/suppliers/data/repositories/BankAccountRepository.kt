package dev.december.jeterbackend.shared.features.suppliers.data.repositories

import dev.december.jeterbackend.shared.features.suppliers.data.entiies.BankAccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BankAccountRepository: JpaRepository<BankAccountEntity, String> {
    fun findBySupplierId(supplierId: String): BankAccountEntity?
}