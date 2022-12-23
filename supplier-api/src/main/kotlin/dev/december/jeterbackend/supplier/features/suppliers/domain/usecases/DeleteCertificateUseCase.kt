package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import org.springframework.stereotype.Component

@Component
class DeleteCertificateUseCase(
    private val supplierService: SupplierService
): UseCase<DeleteCertificateParams, String> {
    override suspend fun invoke(params: DeleteCertificateParams): Data<String> {
        return supplierService.get(params.userId).fold {
                supplier -> supplierService.deleteCertificate(supplier, params.certificateId)
        }
    }
}

data class DeleteCertificateParams(
    val userId: String,
    val certificateId: String
)