package dev.december.jeterbackend.client.features.tour.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.client.features.tour.domain.services.TourService
import org.springframework.stereotype.Component

@Component
class DeleteTourUseCase (
    private val tourService: TourService
) : UseCase<DeleteTourParams, Unit> {
    override suspend fun invoke(params: DeleteTourParams): Data<Unit> {
        return tourService.delete(params.id)
    }
}

data class DeleteTourParams(
    val id: String
)