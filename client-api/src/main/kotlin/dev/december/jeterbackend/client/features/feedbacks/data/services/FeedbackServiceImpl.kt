package dev.december.jeterbackend.client.features.feedbacks.data.services

import dev.december.jeterbackend.client.features.clients.domain.errors.ClientNotFoundFailure
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.client.features.feedbacks.domain.services.FeedbackService
import dev.december.jeterbackend.client.features.feedbacks.domain.errors.FeedbackCreateFailure
import dev.december.jeterbackend.client.features.feedbacks.domain.errors.FeedbackDeleteFailure
import dev.december.jeterbackend.client.features.feedbacks.domain.errors.FeedbackListGetFailure
import dev.december.jeterbackend.client.features.feedbacks.domain.errors.FeedbackNotFoundFailure
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.clients.data.entities.extensions.client
import dev.december.jeterbackend.shared.features.clients.data.repositories.ClientRepository
import dev.december.jeterbackend.shared.features.feedbacks.data.entities.FeedbackEntity
import dev.december.jeterbackend.shared.features.feedbacks.data.repositories.FeedbackRepository
import dev.december.jeterbackend.shared.features.feedbacks.data.repositories.specifications.FeedbackSpecification
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.Feedback
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.FeedbackSortField
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.FeedbackStatus
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.extensions.supplier
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.SupplierRepository
import dev.december.jeterbackend.shared.features.suppliers.domain.errors.SupplierNotFoundFailure
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class FeedbackServiceImpl(
    private val feedbackRepository: FeedbackRepository,
    private val supplierRepository: SupplierRepository,
    private val clientRepository: ClientRepository,
    private val dispatcher: CoroutineDispatcher,
) : FeedbackService {

    override suspend fun delete(id: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val oldEntity =
                    feedbackRepository.findByIdOrNull(id) ?: return@withContext Data.Error(FeedbackNotFoundFailure())
                val newEntity = oldEntity.copy(
                    status = FeedbackStatus.DISAPPROVED
                )
                feedbackRepository.save(newEntity)
                val supplierEntity = newEntity.supplier
                updateSupplierRating(supplierEntity)
                feedbackRepository.delete(newEntity)
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(FeedbackDeleteFailure())
        }
    }

    override suspend fun create(
        userId: String,
        supplierId: String,
        grade: Float,
        description: String?,
    ): Data<String> {
        return try {
            withContext(dispatcher) {
//                val clientEntity = userRepository.findByIdOrNull(userId)?.client
//                    ?: return@withContext Data.Error(ClientNotFoundFailure())
//
//                val supplierEntity =
//                    supplierRepository.findByIdOrNull(supplierId) ?: return@withContext Data.Error(
//                        SupplierNotFoundFailure()
//                    )
//                val feedbackEntity = feedbackRepository.save(
//                    FeedbackEntity(
//                        grade = grade,
//                        description = description ?: "",
//                        client = clientEntity,
//                        supplier = supplierEntity,
//                    )
//                )
//                val rating = feedbackRepository.findAllBySupplierIdAndGetAvg(supplierEntity.id)
//                val oldSupplier = supplierEntity.copy(
//                    rating = String.format("%.1f", rating).toFloat(),
//                    feedbackCount = supplierEntity.feedbackCount + 1
//                )
//                supplierRepository.save(oldSupplier)
                Data.Success("")//feedbackEntity.id
            }
        } catch (e: Exception) {
            Data.Error(FeedbackCreateFailure())
        }
    }

    override suspend fun updateSupplierRating(supplier: SupplierEntity) {
        val feedbackEntities = feedbackRepository.findAllBySupplierAndStatus(supplier, FeedbackStatus.APPROVED)
        val rating = if (feedbackEntities.isEmpty()) 0F
        else {
            val grades = feedbackEntities.map { it.grade }
            grades.average().toFloat()
        }
        val supplierEntity = supplierRepository.findByIdOrNull(supplier.id)
        if (supplierEntity != null)
            supplierRepository.save(
                supplierEntity.copy(
                    rating = rating,
                )
            )
    }

    override suspend fun getList(
        id: String,
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?,
    ): Data<Page<Feedback>> {
        return try {
            withContext(dispatcher) {
                val clientId = clientRepository.findByIdOrNull(id)?.id ?: return@withContext Data.Error(ClientNotFoundFailure())

                val sortParams = FeedbackSortField.CREATED_AT.getSortFields(SortDirection.DESC)

                val pageable = PageRequest.of(page, size, sortParams)

                val specifications =
                    Specification.where(FeedbackSpecification.isInStatus(setOf( FeedbackStatus.APPROVED)))
                        .and(FeedbackSpecification.isGreaterThanCreatedAt(createdFrom))
                        .and(FeedbackSpecification.isLessThanCreatedAt(createdTo))
                        .and(FeedbackSpecification.clientJoinFilter(clientId))

                val entities = feedbackRepository.findAll(specifications, pageable)

                val feedbacks = entities.map { feedbackEntity ->
                    val supplier = feedbackEntity.supplier.supplier()
                    val client = feedbackEntity.client.client()
                    feedbackEntity.convert<FeedbackEntity, Feedback>(
                        mapOf(
                            "supplier" to supplier,
                            "client" to client,
                        )
                    )
                }
                Data.Success(feedbacks)
            }
        } catch (e: Exception) {
            Data.Error(FeedbackListGetFailure())
        }
    }

    override suspend fun getListBySupplier(
        supplierId: String,
        page: Int,
        size: Int,
    ): Data<Page<Feedback>> {
        return try {
            withContext(dispatcher) {
                val supplierEntity = supplierRepository.findByIdOrNull(supplierId)
                    ?: return@withContext Data.Error(SupplierNotFoundFailure())

                if (supplierEntity.enableStatus != AccountEnableStatus.ENABLED || supplierEntity.status != SupplierStatus.APPROVED)
                    return@withContext Data.Error(SupplierNotFoundFailure())

                val sortParams = FeedbackSortField.CREATED_AT.getSortFields(SortDirection.DESC)

                val pageable = PageRequest.of(page, size, sortParams)

                val specifications =
                    Specification.where(FeedbackSpecification.isInStatus(setOf( FeedbackStatus.APPROVED)))
                        .and(FeedbackSpecification.supplierJoinFilter(supplierEntity.id))

                val entities = feedbackRepository.findAll(specifications, pageable)

                val feedbacks: Page<Feedback> = entities.map { feedback ->
                    val supplier = feedback.supplier.supplier()
                    val client = feedback.client.client()
                    feedback.convert<FeedbackEntity, Feedback>(
                        mapOf("supplier" to supplier, "client" to client))
                }

                Data.Success(feedbacks)
            }
        } catch (e: Exception) {
            Data.Error(FeedbackListGetFailure())
        }
    }
}