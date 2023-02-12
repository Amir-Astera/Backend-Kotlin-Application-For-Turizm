package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.model.Language
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import org.springframework.stereotype.Component

@Component
class UpdateLanguageUseCase (
    private val supplierService: SupplierService,
) : UseCase<UpdateLanguageUseCaseParams, Unit> {
    override suspend fun invoke(params: UpdateLanguageUseCaseParams): Data<Unit> {
        return supplierService.updateLanguage(
            params.userId,
            params.language,
        )
    }
}

data class UpdateLanguageUseCaseParams(
    val userId: String,
    val language: Language,
)