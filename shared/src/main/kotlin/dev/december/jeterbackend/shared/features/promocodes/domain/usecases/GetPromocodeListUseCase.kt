package dev.december.jeterbackend.shared.features.promocodes.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.promocodes.domain.services.PromocodeService
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.features.promocodes.domain.models.Promocode
import dev.december.jeterbackend.shared.features.promocodes.domain.models.PromocodeDiscountType
import dev.december.jeterbackend.shared.features.promocodes.domain.models.PromocodeSortField
import dev.december.jeterbackend.shared.features.promocodes.domain.models.PromocodeStatus
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class GetPromocodeListUseCase(
    private val promocodeService: PromocodeService,
) : UseCase<GetPromocodeListParams, Page<Promocode>> {
    override suspend fun invoke(params: GetPromocodeListParams): Data<Page<Promocode>> {
        return promocodeService.getList(
            params.userId, params.supplierId, params.statuses, params.types, params.searchField, params.sortField,
            params.sortDirection, params.page, params.size, params.createdFrom, params.createdTo)
    }
}

data class GetPromocodeListParams(
    val statuses: Set<PromocodeStatus>?,
    val types: Set<PromocodeDiscountType>?,
    val sortField: PromocodeSortField,
    val sortDirection: SortDirection,
    val page: Int,
    val size: Int,
    val createdFrom: LocalDateTime?,
    val createdTo: LocalDateTime?,
    val searchField: String?,
    val userId: String? = null,
    val supplierId: String? = null,
)