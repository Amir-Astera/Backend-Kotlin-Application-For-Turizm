package dev.december.jeterbackend.client.features.appointments.domain.usecases

import dev.december.jeterbackend.client.features.appointments.domain.services.AppointmentService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component


@Component
class ConfirmAppointmentUseCase (
    private val appointmentService: AppointmentService
) : UseCase<ConfirmAppointmentParams, String> {
    override suspend fun invoke(params: ConfirmAppointmentParams): Data<String> {
        return appointmentService.confirm(params.id)
    }
}

data class ConfirmAppointmentParams(
    val id: String
)