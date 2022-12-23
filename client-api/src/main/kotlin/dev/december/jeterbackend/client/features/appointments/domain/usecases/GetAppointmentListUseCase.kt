package dev.december.jeterbackend.client.features.appointments.domain.usecases

import dev.december.jeterbackend.client.features.appointments.domain.model.ClientAppointment
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.client.features.appointments.domain.services.AppointmentService
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentStatus
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime

@Component
class GetAppointmentListUseCase (
    private val appointmentService: AppointmentService
) : UseCase<GetAppointmentsParams, Map<LocalDate, List<ClientAppointment>>> {
    override suspend fun invoke(params: GetAppointmentsParams): Data<Map<LocalDate, List<ClientAppointment>>> {
        return appointmentService.getAll(
            params.id, params.statuses, params.reservationDateFrom, params.reservationDateTo
        )
    }
}

data class GetAppointmentsParams(
    val id: String,
    val statuses: Set<AppointmentStatus>,
    val reservationDateFrom: LocalDateTime,
    val reservationDateTo: LocalDateTime,
)