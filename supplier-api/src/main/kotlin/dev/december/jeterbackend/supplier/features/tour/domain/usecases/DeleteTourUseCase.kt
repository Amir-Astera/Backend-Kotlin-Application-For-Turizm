package dev.december.jeterbackend.supplier.features.tour.domain.usecases

import dev.december.jeterbackend.supplier.features.tour.domain.services.TourService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
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