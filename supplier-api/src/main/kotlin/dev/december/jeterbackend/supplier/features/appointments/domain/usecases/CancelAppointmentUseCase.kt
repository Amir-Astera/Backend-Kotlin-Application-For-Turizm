package dev.december.jeterbackend.supplier.features.appointments.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.appointments.domain.services.AppointmentService
import org.springframework.stereotype.Component


@Component
class CancelAppointmentUseCase(
    private val appointmentService: AppointmentService
) : UseCase<CancelAppointmentParams, String> {
    override suspend fun invoke(params: CancelAppointmentParams): Data<String> {
        return appointmentService.cancel(params.id)
    }
}

data class CancelAppointmentParams(
    val id: String,
)