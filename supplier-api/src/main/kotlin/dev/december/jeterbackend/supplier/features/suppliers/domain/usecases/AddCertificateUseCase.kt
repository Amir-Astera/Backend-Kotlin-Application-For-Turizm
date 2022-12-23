package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierCertificate
import org.springframework.stereotype.Component

@Component
class AddCertificateUseCase(
    private val supplierService: SupplierService
): UseCase<AddCertificateParams, String> {
    override suspend fun invoke(params: AddCertificateParams): Data<String> {
        return supplierService.get(params.userId).fold {
            supplier -> supplierService.addCertificate(supplier, params.education)
        }
    }
}

data class AddCertificateParams(
    val userId: String,
    val education: SupplierCertificate
)