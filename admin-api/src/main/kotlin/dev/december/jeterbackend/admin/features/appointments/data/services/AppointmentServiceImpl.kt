package dev.december.jeterbackend.admin.features.appointments.data.services

import dev.december.jeterbackend.admin.features.appointments.domain.errors.AppointmentGetFailure
import dev.december.jeterbackend.admin.features.appointments.domain.errors.AppointmentGetListFailure
import dev.december.jeterbackend.admin.features.appointments.domain.errors.AppointmentNotFoundFailure
import dev.december.jeterbackend.admin.features.appointments.domain.services.AppointmentService
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.appointments.data.entities.extensions.appointment
import dev.december.jeterbackend.shared.features.appointments.data.repositories.AppointmentRepository
import dev.december.jeterbackend.shared.features.appointments.data.repositories.specification.AppointmentSpecification
import dev.december.jeterbackend.shared.features.appointments.domain.models.Appointment
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentSortField
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentStatus
import dev.december.jeterbackend.shared.features.appointments.domain.models.CommunicationType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class AppointmentServiceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val appointmentRepository: AppointmentRepository,
) : AppointmentService {

    override suspend fun get(
        id: String,
        authority: PlatformRole,
        appointmentId: String
    ): Data<Appointment> {
        return try {
            withContext(dispatcher) {

                val appointmentEntity = if(authority == PlatformRole.CLIENT) {
                    appointmentRepository.findByIdAndClientId(appointmentId, id)
                        ?: return@withContext Data.Error(AppointmentNotFoundFailure())
                } else {
                    appointmentRepository.findByIdAndSupplierId(appointmentId, id)
                        ?: return@withContext Data.Error(AppointmentNotFoundFailure())
                }

                Data.Success(appointmentEntity.appointment())
            }
        } catch (e: Exception) {
            Data.Error(AppointmentGetFailure())
        }
    }

    override suspend fun getAll(
        id: String,
        authority: PlatformRole,
        communicationTypes: Set<CommunicationType>?,
        statuses: Set<AppointmentStatus>?,
        sortField: AppointmentSortField,
        sortDirection: SortDirection,
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?
    ): Data<Page<Appointment>> {
        return try {
            withContext(dispatcher) {
                val sortParams = sortField.getSortFields(sortDirection, authority)

                val pageable = PageRequest.of(page, size, sortParams)

                val specificationByUserRole = if (authority == PlatformRole.CLIENT) {
                    AppointmentSpecification.clientJoinFilter(id)
                } else {
                    AppointmentSpecification.supplierJoinFilter(id)
                }

                var specifications =
                    Specification.where(specificationByUserRole)
                        .and(AppointmentSpecification.hasCommunicationType(communicationTypes))
                        .and(AppointmentSpecification.hasStatus(statuses))

                if (createdFrom != null || createdTo != null) {
                    specifications = specifications.and(AppointmentSpecification.isInPeriod(createdFrom, createdTo))
                }

                val appointmentsEntity = appointmentRepository.findAll(specifications, pageable)

                val appointments = appointmentsEntity.map { it.appointment() }
                Data.Success(appointments)
            }
        } catch (e: Exception) {
            Data.Error(AppointmentGetListFailure())
        }
    }
}