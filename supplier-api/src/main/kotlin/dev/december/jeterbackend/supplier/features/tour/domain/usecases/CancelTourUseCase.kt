package dev.december.jeterbackend.supplier.features.tour.domain.usecases

import dev.december.jeterbackend.supplier.features.tour.domain.services.TourService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component


//@Component
//class CancelTourUseCase(
//    private val tourService: TourService
//) : UseCase<CancelTourParams, String> {
//    override suspend fun invoke(params: CancelTourParams): Data<String> {
//        return tourService.cancel(params.id)
//    }
//}
//
//data class CancelTourParams(
//    val id: String,
//)