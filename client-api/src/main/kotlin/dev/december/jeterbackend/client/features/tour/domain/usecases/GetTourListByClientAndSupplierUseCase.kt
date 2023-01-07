package dev.december.jeterbackend.client.features.tour.domain.usecases

import dev.december.jeterbackend.client.features.tour.domain.model.ClientTour
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.client.features.tour.domain.services.TourService
import org.springframework.stereotype.Component
import java.time.LocalDate

//@Component
//class GetTourListByClientAndSupplierUseCase (
//    private val tourService: TourService
//) : UseCase<GetTourListByClientAndSupplierParams, Map<LocalDate, List<ClientTour>>> {
//    override suspend fun invoke(
//        params: GetTourListByClientAndSupplierParams
//    ): Data<Map<LocalDate, List<ClientTour>>> {
//        return tourService.getAllByClientAndSupplier(
//            params.clientId,
//            params.supplierId
//        )
//    }
//}
//
//data class GetTourListByClientAndSupplierParams(
//    val clientId: String,
//    val supplierId: String
//)