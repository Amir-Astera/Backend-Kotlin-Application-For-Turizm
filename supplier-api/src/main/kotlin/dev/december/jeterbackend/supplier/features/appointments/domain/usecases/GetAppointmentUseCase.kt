package dev.december.jeterbackend.supplier.features.appointments.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.appointments.domain.models.Appointment
import dev.december.jeterbackend.supplier.features.appointments.domain.services.AppointmentService
import org.springframework.stereotype.Component

@Component
class GetAppointmentUseCase(
    private val appointmentService: AppointmentService
) : UseCase<GetAppointmentParams, Appointment> {
    override suspend fun invoke(params: GetAppointmentParams): Data<Appointment> {
        return appointmentService.get(params.id, params.appointmentId)
    }
}

data class GetAppointmentParams(
    val id: String,
    val appointmentId: String
)