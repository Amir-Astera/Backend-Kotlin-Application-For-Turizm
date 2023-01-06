package dev.december.jeterbackend.client.features.payments.data.services

import dev.december.jeterbackend.client.features.clients.domain.errors.ClientNotFoundFailure
import dev.december.jeterbackend.client.features.payments.domain.errors.PaymentCreateFailure
import dev.december.jeterbackend.client.features.payments.domain.errors.PaymentGetListFailure
import dev.december.jeterbackend.client.features.payments.domain.services.PaymentService
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.appointments.data.entities.extensions.appointment
import dev.december.jeterbackend.shared.features.appointments.data.repositories.AppointmentRepository
import dev.december.jeterbackend.shared.features.appointments.data.repositories.specification.AppointmentSpecification
import dev.december.jeterbackend.shared.features.appointments.domain.models.Appointment
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentSortField
import dev.december.jeterbackend.shared.features.clients.data.repositories.ClientRepository
import dev.december.jeterbackend.shared.features.tours.data.repositories.TourRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*


@Service
class PaymentServiceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val clientRepository: ClientRepository,
    private val tourRepository: TourRepository,
    private val appointmentRepository: AppointmentRepository
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
    ): Data<Page<Appointment>> {
        return try {
            withContext(dispatcher) {
                val client = clientRepository.findByIdOrNull(userId) ?: return@withContext Data.Error(ClientNotFoundFailure())

                val sortParams = AppointmentSortField.RESERVATION_DATE.getSortFields(SortDirection.DESC, PlatformRole.CLIENT)

                val pageable = PageRequest.of(page, size, sortParams)

                var specifications =
                    Specification.where(AppointmentSpecification.clientJoinFilter(client.id))
                        .and(AppointmentSpecification.paymentIsNotNull())

                if (createdFrom != null || createdTo != null) {
                    specifications = specifications.and(AppointmentSpecification.isInPeriod(createdFrom, createdTo))
                }

                val appointmentsEntity = appointmentRepository.findAll(specifications, pageable)

                val payments = appointmentsEntity.map { it.appointment() }

                Data.Success(payments)
            }
        } catch (e: Exception) {
            Data.Error(PaymentGetListFailure())
        }
    }
}