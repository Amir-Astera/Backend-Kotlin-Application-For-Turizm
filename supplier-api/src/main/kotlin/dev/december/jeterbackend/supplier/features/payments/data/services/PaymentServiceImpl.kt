package dev.december.jeterbackend.supplier.features.payments.data.services

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.clients.data.entities.extensions.client
import dev.december.jeterbackend.shared.features.clients.data.repositories.ClientRepository
import dev.december.jeterbackend.shared.features.payments.domain.models.PaymentBySupplier
import dev.december.jeterbackend.shared.features.payments.domain.models.PaymentBySupplierList
import dev.december.jeterbackend.shared.features.payments.domain.models.PaymentList
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.SupplierRepository
import dev.december.jeterbackend.shared.features.tours.data.repositories.TourRepository
import dev.december.jeterbackend.shared.features.tours.data.repositories.specification.TourSpecification
import dev.december.jeterbackend.shared.features.tours.domain.models.TourSortField
import dev.december.jeterbackend.supplier.features.payments.domain.errors.PaymentCreateFailure
import dev.december.jeterbackend.supplier.features.payments.domain.errors.PaymentListNotFoundFailure
import dev.december.jeterbackend.supplier.features.payments.domain.errors.PaymentNotFoundFailure
import dev.december.jeterbackend.supplier.features.payments.domain.errors.SessionNotFoundFailure
import dev.december.jeterbackend.supplier.features.payments.domain.services.PaymentService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*


@Service
class PaymentServiceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val supplierRepository: SupplierRepository,
    private val clientRepository: ClientRepository,
    private val tourRepository: TourRepository
) : PaymentService {

    override suspend fun create(
        name: String,
    ): Data<String> {
        return try {
            withContext(dispatcher) {
                Data.Success(UUID.randomUUID().toString())
            }
        } catch (e: Exception) {
            Data.Error(PaymentCreateFailure())
        }
    }

    override suspend fun getAll(
        id: String,
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?
    ): Data<Unit> {//PaymentList
        return try {
            withContext(dispatcher) {
//                val user = userRepository.findByIdOrNull(id) ?:
//                    return@withContext Data.Error(UserNotFoundFailure())
//
//                val supplierId = user.supplier?.id ?:
//                    return@withContext Data.Error(SupplierNotFoundFailure())
//
//                val sortParams =
//                    TourSortField.RESERVATION_DATE.getSortFields(SortDirection.DESC, PlatformRole.SUPPLIER )
//
//                val pageable = PageRequest.of(page, size, sortParams)
//
//                var specifications =
//                    Specification.where(TourSpecification.supplierJoinFilter(supplierId))
//                        .and(TourSpecification.paymentIsNotNull())

//                if (createdFrom != null || createdTo != null) {
//                    specifications = specifications.and(TourSpecification.isInPeriod(createdFrom, createdTo))
//                }
//
//                val appointmentsEntity = tourRepository.findAll(specifications, pageable)
//
//                val payments = appointmentsEntity.map { it.payment() }
//
//                val totalPayments = if (page == 0) {
//                    if (createdFrom != null && createdTo != null)
//                        tourRepository.getTotalPaymentsInPeriod(supplierId, createdFrom, createdTo) ?: 0
//                    else if (createdFrom != null)
//                        tourRepository.getTotalPaymentsFromCreated(supplierId, createdFrom) ?: 0
//                    else if (createdTo != null)
//                        tourRepository.getTotalPaymentsToCreated(supplierId, createdTo) ?: 0
//                    else
//                        tourRepository.getTotalPayments(supplierId) ?: 0
//                } else 0
//
//                val paymentList = PaymentList(
//                    total = totalPayments,
//                    payments = payments
//                )

                Data.Success(Unit)//paymentList
            }
        } catch (e: Exception) {
            Data.Error(PaymentListNotFoundFailure())
        }
    }

    override suspend fun getPaymentsHistoryByClients(
        id: String,
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?
    ): Data<Unit> {//PaymentBySupplierList
        return try {
            withContext(dispatcher) {
//                val user = userRepository.findByIdOrNull(id) ?:
//                    return@withContext Data.Error(UserNotFoundFailure())
//
//                val supplierId = user.supplier?.id ?:
//                    return@withContext Data.Error(SupplierNotFoundFailure())
//
//                val pageable = PageRequest.of(page, size)
//
//                val entities =
//                    if (createdFrom != null && createdTo != null)
//                        tourRepository.getPaymentsAndGroupBySupplierIdInPeriod(supplierId, pageable, createdFrom, createdTo)
//                    else if (createdFrom != null)
//                        tourRepository.getPaymentsAndGroupBySupplierIdFromCreated(supplierId, pageable, createdFrom)
//                    else if (createdTo != null)
//                        tourRepository.getPaymentsAndGroupBySupplierIdToCreated(supplierId, pageable, createdTo)
//                    else
//                        tourRepository.getPaymentsAndGroupBySupplierId(supplierId, pageable)
//
//                val clientIds = entities.map { it.getClient() }
//
//                val clients = clientRepository.findAllById(clientIds).associateBy { it.id }
//
//                val totalPayments = if (page == 0) {
//                    if (createdFrom != null && createdTo != null)
//                        tourRepository.getTotalPaymentsInPeriod(supplierId, createdFrom, createdTo) ?: 0
//                    else if (createdFrom != null)
//                        tourRepository.getTotalPaymentsFromCreated(supplierId, createdFrom) ?: 0
//                    else if (createdTo != null)
//                        tourRepository.getTotalPaymentsToCreated(supplierId, createdTo) ?: 0
//                    else
//                        tourRepository.getTotalPayments(supplierId) ?: 0
//                } else 0
//
//                val paymentsMap = mutableMapOf<String, PaymentBySupplier>()
//
//                for(payment in entities) {
//                    paymentsMap[payment.getClient()] = PaymentBySupplier(
//                        clients[payment.getClient()]?.client() ?: return@withContext Data.Error(ClientNotFoundFailure()),
//                        payment.getPayment() ?: return@withContext Data.Error(PaymentNotFoundFailure()),
//                        payment.getSessionCount()  ?: return@withContext Data.Error(SessionNotFoundFailure()),
//                    )
//                }
//
//                val payments = PaymentBySupplierList(
//                    total = totalPayments,
//                    payments = entities.map {
//                        paymentsMap[it.getClient()]
//                    }
//                )
//

                Data.Success(Unit)//payments
            }
        } catch (e: Exception) {
            println(e.message)
            Data.Error((PaymentListNotFoundFailure()))
        }
    }

    override suspend fun getPaymentsHistoryByClient(
        id: String,
        clientId: String,
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?
    ): Data<Unit> {//PaymentLis
        return try {
            withContext(dispatcher) {
//                val user = userRepository.findByIdOrNull(id) ?:
//                    return@withContext Data.Error(UserNotFoundFailure())
//
//                val supplierId = user.supplier?.id ?:
//                    return@withContext Data.Error(SupplierNotFoundFailure())
//
//                val sortParams =
//                    TourSortField.RESERVATION_DATE.getSortFields(SortDirection.DESC, PlatformRole.SUPPLIER )
//
//                val pageable = PageRequest.of(page, size, sortParams)
//
//                var specifications =
//                    Specification.where(TourSpecification.supplierJoinFilter(supplierId))
//                        .and(TourSpecification.clientJoinFilter(clientId))
//                        .and(TourSpecification.paymentIsNotNull())
//
//                if (createdFrom != null || createdTo != null) {
//                    specifications = specifications.and(TourSpecification.isInPeriod(createdFrom, createdTo))
//                }
//
//                val appointmentsEntity = tourRepository.findAll(specifications, pageable)
//
//                val payments = appointmentsEntity.map { it.payment() }
//
//                val totalPayments = if (page == 0) {
//                    if (createdFrom != null && createdTo != null)
//                        tourRepository.getTotalPaymentsByClientInPeriod(supplierId, clientId, createdFrom, createdTo) ?: 0
//                    else if (createdFrom != null)
//                        tourRepository.getTotalPaymentsByClientFromCreated(supplierId, clientId, createdFrom) ?: 0
//                    else if (createdTo != null)
//                        tourRepository.getTotalPaymentsByClientToCreated(supplierId, clientId, createdTo) ?: 0
//                    else
//                        tourRepository.getTotalPaymentsByClient(supplierId, clientId) ?: 0
//                } else 0
//
//                val paymentList = PaymentList(
//                    total = totalPayments,
//                    payments = payments
//                )

                Data.Success(Unit)//paymentList
            }
        } catch (e: Exception) {
            Data.Error((PaymentListNotFoundFailure()))
        }
    }
}