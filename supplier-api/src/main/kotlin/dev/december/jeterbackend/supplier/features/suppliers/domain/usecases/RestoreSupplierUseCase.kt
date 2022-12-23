package dev.december.jeterbackend.supplier.features.suppliers.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.supplier.features.suppliers.domain.services.SupplierService
import org.springframework.stereotype.Component

@Component
class RestoreSupplierUseCase(private val service: SupplierService): UseCase<RestoreSupplierParams, Unit>{
    override suspend fun invoke(params: RestoreSupplierParams): Data<Unit> {
        return service.restore(params.id, params.signInProvider)
    }
}

data class RestoreSupplierParams(
 val id: String,
 val signInProvider: String?
)