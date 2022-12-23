package dev.december.jeterbackend.client.features.tour.domain.usecases

import dev.december.jeterbackend.client.features.tour.domain.model.ClientTour
import dev.december.jeterbackend.client.features.tour.domain.services.TourService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class GetAppointmentUseCase(
    private val tourService: TourService
) : UseCase<GetAppointmentParams, ClientTour> {
    override suspend fun invoke(params: GetAppointmentParams): Data<ClientTour> {
        return tourService.get(params.id, params.appointmentId)
    }
}

data class GetAppointmentParams(
    val id: String,
    val appointmentId: String
)