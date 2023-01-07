package dev.december.jeterbackend.client.features.tour.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.client.features.tour.domain.services.TourService
import dev.december.jeterbackend.shared.features.appointments.domain.models.Appointment
import dev.december.jeterbackend.shared.features.tours.domain.models.TourStatus
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime

@Component
class GetTourListUseCase (
    private val tourService: TourService
) : UseCase<GetToursParams, Map<LocalDate, List<Appointment>>> {
    override suspend fun invoke(params: GetToursParams): Data<Map<LocalDate, List<Appointment>>> {//Нужно изменить на лист аппойнментов
        return tourService.getAll(
            params.id, params.statuses, params.reservationDateFrom, params.reservationDateTo
        )
    }
}

data class GetToursParams(
    val id: String,
    val statuses: Set<TourStatus>,
    val reservationDateFrom: LocalDateTime,
    val reservationDateTo: LocalDateTime,
)