package dev.december.jeterbackend.admin.features.admin.domain.usecases

import dev.december.jeterbackend.admin.features.admin.domain.services.AdminService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class DeleteAdminByIdUseCase (
    private val adminService: AdminService
) : UseCase<DeleteAdminByIdParams, Unit> {
    override suspend fun invoke(params: DeleteAdminByIdParams): Data<Unit> {
        return adminService.delete(params.adminId)
    }
}

data class DeleteAdminByIdParams(
    val adminId: String
)