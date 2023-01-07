package dev.december.jeterbackend.client.features.suppliers.domain.usecases

import dev.december.jeterbackend.client.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.core.domain.model.Gender
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import dev.december.jeterbackend.shared.features.suppliers.domain.models.enums.SupplierAgeRange
import dev.december.jeterbackend.shared.features.suppliers.domain.models.enums.SupplierExperienceRange
import dev.december.jeterbackend.shared.features.suppliers.domain.models.enums.SupplierPriceRange
import dev.december.jeterbackend.shared.features.suppliers.domain.models.enums.SupplierRatingRange
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class GetSupplierListUseCase(
    private val supplierService: SupplierService,
) : UseCase<GetSupplierListParams, Page<Supplier>> {
    override suspend fun invoke(params: GetSupplierListParams): Data<Page<Supplier>> {
        return supplierService.getList(
            params.userId,
            params.page,
            params.size,
            params.searchField,
            params.ratingFilter,
            params.experienceFilter,
            params.ageFilter,
            params.genderFilter,
            params.priceFilter,
        )
    }
}

data class GetSupplierListParams(
    val userId: String,
    val page: Int,
    val size: Int,
    val searchField: String?,
    val ratingFilter: SupplierRatingRange?,
    val experienceFilter: SupplierExperienceRange?,
    val ageFilter: SupplierAgeRange?,
    val genderFilter: Gender?,
    val priceFilter: SupplierPriceRange?,
)