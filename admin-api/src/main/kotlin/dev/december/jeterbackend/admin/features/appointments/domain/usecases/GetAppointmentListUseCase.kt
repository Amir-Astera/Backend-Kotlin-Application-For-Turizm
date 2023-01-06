package dev.december.jeterbackend.admin.features.appointments.domain.usecases

import dev.december.jeterbackend.admin.features.appointments.domain.services.AppointmentService
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.appointments.domain.models.Appointment
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentSortField
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentStatus
import dev.december.jeterbackend.shared.features.appointments.domain.models.CommunicationType
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class GetAppointmentListUseCase (
    private val appointmentService: AppointmentService
) : UseCase<GetAppointmentsParams, Page<Appointment>> {
    override suspend fun invoke(params: GetAppointmentsParams): Data<Page<Appointment>> {
        return appointmentService.getAll(
            params.id, params.authority, params.communicationTypes,
            params.statuses, params.sortField, params.sortDirection,
            params.page, params.size, params.createdFrom, params.createdTo
        )
    }
}

data class GetAppointmentsParams(
    val id: String,
    val authority: PlatformRole,
    val communicationTypes: Set<CommunicationType>?,
    val statuses: Set<AppointmentStatus>?,
    val sortField: AppointmentSortField,
    val sortDirection: SortDirection,
    val page: Int,
    val size: Int,
    val createdFrom: LocalDateTime?,
    val createdTo: LocalDateTime?,
)