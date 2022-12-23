package dev.december.jeterbackend.client.features.appointments.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.client.features.appointments.domain.services.AppointmentService
import org.springframework.stereotype.Component



@Component
class CompleteAppointmentUseCase (
    private val appointmentService: AppointmentService
) : UseCase<CompleteAppointmentParams, String> {
    override suspend fun invoke(params: CompleteAppointmentParams): Data<String> {
        return appointmentService.complete(params.id)
    }
}

data class CompleteAppointmentParams(
    val id: String,
)