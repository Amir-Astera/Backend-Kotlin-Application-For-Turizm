package dev.december.jeterbackend.client.features.appointments.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.client.features.appointments.domain.services.AppointmentService
import org.springframework.stereotype.Component

@Component
class DeleteAppointmentUseCase (
    private val appointmentService: AppointmentService
) : UseCase<DeleteAppointmentParams, Unit> {
    override suspend fun invoke(params: DeleteAppointmentParams): Data<Unit> {
        return appointmentService.delete(params.id)
    }
}

data class DeleteAppointmentParams(
    val id: String
)