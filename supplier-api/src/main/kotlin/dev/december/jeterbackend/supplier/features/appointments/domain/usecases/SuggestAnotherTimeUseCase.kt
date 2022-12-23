package dev.december.jeterbackend.supplier.features.appointments.domain.usecases

import dev.december.jeterbackend.supplier.features.appointments.domain.services.AppointmentService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class SuggestAnotherTimeUseCase (
    private val appointmentService: AppointmentService
) : UseCase<SuggestAnotherTimeParams, String> {
    override suspend fun invoke(params: SuggestAnotherTimeParams): Data<String> {
        return appointmentService.suggestAnotherTime(params.userId,params.id, params.reservationDateTime)
    }
}

data class SuggestAnotherTimeParams(
    val userId: String,
    val id: String,
    val reservationDateTime: LocalDateTime
)