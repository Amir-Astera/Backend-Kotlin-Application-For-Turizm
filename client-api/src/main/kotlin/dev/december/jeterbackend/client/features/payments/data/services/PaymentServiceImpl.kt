package dev.december.jeterbackend.client.features.payments.data.services

import dev.december.jeterbackend.client.features.payments.domain.errors.PaymentCreateFailure
import dev.december.jeterbackend.client.features.payments.domain.errors.PaymentGetListFailure
import dev.december.jeterbackend.client.features.payments.domain.services.PaymentService
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.clients.data.repositories.ClientRepository
import dev.december.jeterbackend.shared.features.tours.data.repositories.TourRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*


@Service
class PaymentServiceImpl(
    private val dispatcher: CoroutineDispatcher,
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
        userId: String,
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?
    ): Data<Unit> {//List<Tour>
        return try {
            withContext(dispatcher) {
//                val client = (userRepository.findByIdOrNull(userId)
//                    ?: return@withContext Data.Error(UserNotFoundFailure())).client
//                    ?: return@withContext Data.Error(ClientNotFoundFailure())
//                val sortParams = AppointmentSortField.RESERVATION_DATE.getSortFields(SortDirection.DESC, PlatformRole.CLIENT)
//
//                val pageable = PageRequest.of(page, size, sortParams)
//
//                val payments = if(createdFrom != null && createdTo != null) {
//                    tourRepository
//                        .findAllByClientIdAndPaymentNotNullAndReservationDateBetween(
//                            client.id, createdFrom, createdTo, pageable
//                        )
//                } else if (createdFrom != null) {
//                    tourRepository.findAllByClientIdAndPaymentNotNullAndReservationDateAfter(
//                        client.id, createdFrom, pageable
//                    )
//                } else if (createdTo != null) {
//                    tourRepository.findAllByClientIdAndPaymentNotNullAndReservationDateBefore(
//                        client.id, createdTo, pageable
//                    )
//                } else {
//                    tourRepository.findAllByClientIdAndPaymentNotNull(client.id, pageable)
//                }
                Data.Success(Unit)//payments.map { it.appointment() }
            }
        } catch (e: Exception) {
            Data.Error(PaymentGetListFailure())
        }
    }
}