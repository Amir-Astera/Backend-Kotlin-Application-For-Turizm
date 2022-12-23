package dev.december.jeterbackend.supplier.features.tour.domain.usecases

import dev.december.jeterbackend.supplier.features.tour.domain.services.TourService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.tour.presentation.dto.FreeTimeDto
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class GetSupplierFreeTimeUseCase (
    private val tourService: TourService
) : UseCase<GetSupplierFreeTimeParams, List<FreeTimeDto>> {
    override suspend fun invoke(params: GetSupplierFreeTimeParams): Data<List<FreeTimeDto>> {
        return tourService.getSupplierFreeTime(params.userId, params.date)
    }
}

data class GetSupplierFreeTimeParams(
    val userId: String,
    val date: LocalDate
)