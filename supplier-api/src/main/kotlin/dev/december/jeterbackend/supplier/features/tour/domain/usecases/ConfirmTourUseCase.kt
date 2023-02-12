package dev.december.jeterbackend.supplier.features.tour.domain.usecases

import dev.december.jeterbackend.supplier.features.tour.domain.services.TourService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component


//@Component
//class ConfirmTourUseCase (
//    private val tourService: TourService
//) : UseCase<ConfirmTourParams, String> {
//    override suspend fun invoke(params: ConfirmTourParams): Data<String> {
//        return tourService.confirm(params.id)
//    }
//}
//
//data class ConfirmTourParams(
//    val id: String
//)