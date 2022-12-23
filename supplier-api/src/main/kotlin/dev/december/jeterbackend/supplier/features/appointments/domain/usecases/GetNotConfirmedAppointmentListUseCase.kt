package dev.december.jeterbackend.supplier.features.appointments.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.appointments.domain.models.Appointment
import dev.december.jeterbackend.supplier.features.appointments.domain.services.AppointmentService
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class GetNotConfirmedAppointmentListUseCase(
    private val appointmentService: AppointmentService
) : UseCase<GetNotConfirmedAppointmentListParams, Page<Appointment>> {
    override suspend fun invoke(params: GetNotConfirmedAppointmentListParams): Data<Page<Appointment>> {
        return appointmentService.getListOfNotConfirmedAppointments(
            params.userId,
            params.page,
            params.size
        )
    }
}

data class GetNotConfirmedAppointmentListParams(
    val userId: String,
    val page: Int,
    val size: Int,
)