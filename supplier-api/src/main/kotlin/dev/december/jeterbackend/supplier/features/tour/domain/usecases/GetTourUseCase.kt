package dev.december.jeterbackend.supplier.features.tour.domain.usecases

import dev.december.jeterbackend.supplier.features.tour.domain.services.TourService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.tours.domain.models.Tour
import org.springframework.stereotype.Component

@Component
class GetTourUseCase(
    private val tourService: TourService
) : UseCase<GetTourParams, Tour> {
    override suspend fun invoke(params: GetTourParams): Data<Tour> {
        return tourService.get(params.id, params.appointmentId)
    }
}

data class GetTourParams(
    val id: String,
    val appointmentId: String
)