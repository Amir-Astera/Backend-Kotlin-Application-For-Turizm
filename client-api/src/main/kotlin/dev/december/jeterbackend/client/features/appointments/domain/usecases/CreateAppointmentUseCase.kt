package dev.december.jeterbackend.client.features.appointments.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.appointments.domain.models.CommunicationType
import dev.december.jeterbackend.client.features.appointments.domain.services.AppointmentService
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CreateAppointmentUseCase(
    private val appointmentService: AppointmentService
) : UseCase<CreateAppointmentParams, String> {
    override suspend fun invoke(params: CreateAppointmentParams): Data<String> {
        return appointmentService.create(
            params.reservationDate,
            params.communicationType,
            params.description,
            params.userId,
            params.supplierId,

        )
    }
}

data class CreateAppointmentParams(
    val reservationDate: LocalDateTime,
    val communicationType: CommunicationType,
    val description: String,
    val userId: String,
    val supplierId: String,
)