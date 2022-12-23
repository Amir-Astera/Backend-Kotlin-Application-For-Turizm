package dev.december.jeterbackend.shared.features.feedbacks.data.repositories

import dev.december.jeterbackend.shared.features.feedbacks.data.entities.FeedbackEntity
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.FeedbackStatus
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface FeedbackRepository : JpaRepository<FeedbackEntity, String>,
    JpaSpecificationExecutor<FeedbackEntity> {
    fun findAllBySupplierAndStatus(
        supplier: SupplierEntity,
        status: FeedbackStatus,
    ): List<FeedbackEntity>

    fun findAllBySupplier(supplier: SupplierEntity, pageable: Pageable): Page<FeedbackEntity>

    fun findAllByClient(client: ClientEntity, pageable: Pageable): Page<FeedbackEntity>

    @Query("select avg(f.grade) from feedback f where f.supplier.id = :supplierId")
    fun findAllBySupplierIdAndGetAvg(supplierId: String): Double

    @Transactional
    fun deleteAllBySupplierIdIn(ids: List<String>)

    @Transactional
    fun deleteAllBySupplierId(id: String)
}