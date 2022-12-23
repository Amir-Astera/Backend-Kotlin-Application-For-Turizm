package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierCertificateUpdate
import org.springframework.stereotype.Component

@Component
class UpdateCertificateUseCase(
    private val supplierService: SupplierService
): UseCase<UpdateCertificateParams, String> {
    override suspend fun invoke(params: UpdateCertificateParams): Data<String> {
        return supplierService.get(params.userId).fold { supplier -> supplierService.updateCertificate(
            supplier,
            params.certificateId,
            params.data
        ) }
    }
}

data class UpdateCertificateParams(
    val userId: String,
    val certificateId: String,
    val data: SupplierCertificateUpdate
)