package dev.december.jeterbackend.supplier.features.tour.domain.usecases

import dev.december.jeterbackend.supplier.features.tour.domain.services.TourService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentStatus
import dev.december.jeterbackend.shared.features.tours.domain.models.Tour
import dev.december.jeterbackend.shared.features.tours.domain.models.TourStatus
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime

@Component
class GetTourListUseCase (
    private val tourService: TourService
) : UseCase<GetToursParams, Map<LocalDate, List<Tour>>> {
    override suspend fun invoke(params: GetToursParams): Data<Map<LocalDate, List<Tour>>> {
        return tourService.getAll(
            params.id, params.statuses, params.reservationDateFrom, params.reservationDateTo
        )
    }
}

data class GetToursParams(
    val id: String,
    val statuses: Set<AppointmentStatus>,
    val reservationDateFrom: LocalDateTime,
    val reservationDateTo: LocalDateTime,
)