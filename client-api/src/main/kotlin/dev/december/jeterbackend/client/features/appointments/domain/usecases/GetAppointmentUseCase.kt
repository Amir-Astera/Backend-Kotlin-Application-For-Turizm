package dev.december.jeterbackend.client.features.appointments.domain.usecases

import dev.december.jeterbackend.client.features.appointments.domain.model.ClientAppointment
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.client.features.appointments.domain.services.AppointmentService
import org.springframework.stereotype.Component

@Component
class GetAppointmentUseCase(
    private val appointmentService: AppointmentService
) : UseCase<GetAppointmentParams, ClientAppointment> {
    override suspend fun invoke(params: GetAppointmentParams): Data<ClientAppointment> {
        return appointmentService.get(params.id, params.appointmentId)
    }
}

data class GetAppointmentParams(
    val id: String,
    val appointmentId: String
)