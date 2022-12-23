package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierSocialMedia
import org.springframework.stereotype.Component

@Component
class PatchSocialMediaUseCase(
    private val supplierService: SupplierService
): UseCase<PatchSocialMediaParams, String> {
    override suspend fun invoke(params: PatchSocialMediaParams): Data<String> {
        return supplierService.updateSocialMedia(params.userId, params.socialMedia)
    }
}

data class PatchSocialMediaParams(
    val userId: String,
    val socialMedia: SupplierSocialMedia
)