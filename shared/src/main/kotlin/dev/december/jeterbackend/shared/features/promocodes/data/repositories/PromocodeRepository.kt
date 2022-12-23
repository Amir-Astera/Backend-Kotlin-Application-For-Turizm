package dev.december.jeterbackend.shared.features.promocodes.data.repositories

import dev.december.jeterbackend.shared.features.promocodes.data.entities.PromocodeEntity
import dev.december.jeterbackend.shared.features.promocodes.domain.models.PromocodeEnableStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface PromocodeRepository : PagingAndSortingRepository<PromocodeEntity, String>,
    JpaSpecificationExecutor<PromocodeEntity>, JpaRepository<PromocodeEntity, String> {
    fun existsByCode(title: String): Boolean
    fun findByCode(title: String): Optional<PromocodeEntity>
    fun findAllByStatus(enableStatus: PromocodeEnableStatus): List<PromocodeEntity>

    @Transactional
    fun deleteAllBySupplierIdIn(ids: List<String>)

    @Transactional
    fun deleteAllBySupplierId(id: String)
}