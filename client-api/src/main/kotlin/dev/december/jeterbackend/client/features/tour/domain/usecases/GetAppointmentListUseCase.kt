package dev.december.jeterbackend.client.features.tour.domain.usecases

import dev.december.jeterbackend.client.features.tour.domain.model.ClientTour
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.client.features.tour.domain.services.TourService
import dev.december.jeterbackend.shared.features.appointments.domain.models.Appointment
import dev.december.jeterbackend.shared.features.tours.domain.models.TourStatus
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime

@Component
class GetAppointmentListUseCase (
    private val tourService: TourService
) : UseCase<GetAppointmentsParams, Map<LocalDate, List<Appointment>>> {
    override suspend fun invoke(params: GetAppointmentsParams): Data<Map<LocalDate, List<Appointment>>> {//Нужно изменить на лист аппойнментов
        return tourService.getAll(
            params.id, params.statuses, params.reservationDateFrom, params.reservationDateTo
        )
    }
}

data class GetAppointmentsParams(
    val id: String,
    val statuses: Set<TourStatus>,
    val reservationDateFrom: LocalDateTime,
    val reservationDateTo: LocalDateTime,
)