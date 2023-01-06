package dev.december.jeterbackend.admin.features.suppliers.data.services

import dev.december.jeterbackend.admin.features.suppliers.domain.errors.*
import dev.december.jeterbackend.admin.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.OsType
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.clients.data.entities.extensions.client
import dev.december.jeterbackend.shared.features.feedbacks.data.entities.FeedbackEntity
import dev.december.jeterbackend.shared.features.feedbacks.data.repositories.FeedbackRepository
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.Feedback
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.FeedbackSortField
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.FeedbackWithGrade
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.extensions.supplier
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.SupplierRepository
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.specifications.SupplierSpecification
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierSortField
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
class SupplierServiceImpl(
    private val supplierRepository: SupplierRepository,
    private val feedbackRepository: FeedbackRepository,
    private val dispatcher: CoroutineDispatcher,
) : SupplierService {

    override suspend fun getList(
        sortField: SupplierSortField,
        sortDirection: SortDirection,
        page: Int,
        size: Int,
        searchField: String?,
        activityStatuses: Set<AccountActivityStatus>?,
        statuses: Set<SupplierStatus>?,
        osTypes: Set<OsType>?,
        professionIds: Set<String>?,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?,
        enableStatus: AccountEnableStatus?,
    ): Data<Page<Supplier>> {
        return try {
            withContext(dispatcher) {
                val sortParams = sortField.getSortFields(sortDirection)

                val pageable = PageRequest.of(page, size, sortParams)

                val searchArray = searchField?.trim()?.split("\\s+".toRegex())?.toTypedArray()

                val specifications =
                    Specification.where(SupplierSpecification.hasEnableStatus(enableStatus))
                        .and(SupplierSpecification.containsName(searchArray))
                        .and(SupplierSpecification.isGreaterThanCreatedAt(createdFrom))
                        .and(SupplierSpecification.isLessThanCreatedAt(createdTo))
                        .and(SupplierSpecification.isInActivityStatus(activityStatuses))
                        .and(SupplierSpecification.isInStatus(statuses))
//                        .and(SupplierSpecification.professionJoinFilter(professionIds))
                        .and(SupplierSpecification.isInOsType(osTypes))

                val entities = supplierRepository.findAll(specifications, pageable)
                val suppliers = entities.map { it.supplier() }
                Data.Success(suppliers)
            }
        } catch (e: Exception) {
            Data.Error(SupplierListGetFailure())
        }
    }

    override suspend fun disableList(ids: List<String>): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val suppliersEntity = supplierRepository.findAllById(ids)
                supplierRepository.saveAll(
                    suppliersEntity.map {
                        it.copy(
                            enableStatus = AccountEnableStatus.DISABLED
                        )
                    }
                )
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(SupplierListDisableFailure())
        }
    }

    override suspend fun disable(supplierId: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val supplierEntity = supplierRepository.findByIdOrNull(supplierId)
                    ?: return@withContext Data.Error(SupplierNotFoundFailure())
                supplierRepository.save(
                    supplierEntity.copy(
                        enableStatus = AccountEnableStatus.DISABLED
                    )
                )
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(SupplierDisableFailure())
        }
    }

    override suspend fun approve(supplierId: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val supplierEntity = supplierRepository.findByIdOrNull(supplierId) ?: return@withContext Data.Error(
                    SupplierNotFoundFailure()
                )
                supplierRepository.save(
                    supplierEntity.copy(
                        status = SupplierStatus.APPROVED,
                    )
                )
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(SupplierApproveFailure())
        }
    }

    override suspend fun disapprove(supplierId: String): Data<Unit> {
        return try {
            withContext(dispatcher){
                val supplierEntity = supplierRepository.findByIdOrNull(supplierId) ?: return@withContext Data.Error(SupplierNotFoundFailure())

                supplierRepository.save(
                    supplierEntity.copy(
                        status = SupplierStatus.DISAPPROVED
                    )
                )
                Data.Success(Unit)
            }
        } catch (e: Exception){
            Data.Error(SupplierDisapproveFailure())
        }
    }

    override suspend fun enable(supplierId: String): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val supplierEntity = supplierRepository.findByIdOrNull(supplierId) ?: return@withContext Data.Error(
                    SupplierNotFoundFailure()
                )
                supplierRepository.save(
                    supplierEntity.copy(
                        enableStatus = AccountEnableStatus.ENABLED
                    )
                )
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(SupplierEnableFailure())
        }
    }

    override suspend fun get(supplierId: String): Data<Supplier> {
        return try {
            withContext(dispatcher) {

                val supplierEntity = supplierRepository.findByIdOrNull(supplierId) ?: return@withContext Data.Error(
                    SupplierNotFoundFailure()
                )
                Data.Success(supplierEntity.supplier())
            }
        } catch (e: Exception) {
            Data.Error(SupplierGetFailure())
        }
    }

    override suspend fun getSupplierFeedbacks(
        supplierId: String,
        sortField: FeedbackSortField,
        sortDirection: SortDirection,
        page: Int,
        size: Int
    ): Data<FeedbackWithGrade> {
        return try {
            withContext(dispatcher) {
                val sortParams = sortField.getSortFields(sortDirection)
                val pageable = PageRequest.of(page, size, sortParams)

                val supplierEntity = supplierRepository.findByIdOrNull(supplierId) ?: return@withContext Data.Error(SupplierNotFoundFailure())
                val feedbackEntity = feedbackRepository.findAllBySupplier(supplierEntity, pageable)

                val averageCount = feedbackRepository.findAllBySupplierIdAndGetAvg(supplierEntity.id)

                val feedbacks = feedbackEntity.map { feedback ->
                    val supplier = feedback.supplier.supplier()
                    val client = feedback.client.client()
                    feedback.convert<FeedbackEntity, Feedback>(
                        mapOf(
                            "supplier" to supplier,
                            "client" to client
                        )
                    )
                }

                val feedbackList = FeedbackWithGrade(averageCount, feedbacks)

                Data.Success(feedbackList)
            }
        } catch (e: Exception) {
            Data.Error(SupplierGetFailure())
        }
    }
}