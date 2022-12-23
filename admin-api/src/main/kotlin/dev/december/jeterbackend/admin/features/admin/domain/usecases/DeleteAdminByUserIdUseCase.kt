package dev.december.jeterbackend.admin.features.admin.domain.usecases

import dev.december.jeterbackend.admin.features.admin.domain.services.AdminService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class DeleteAdminByUserIdUseCase (
    private val adminService: AdminService
) : UseCase<DeleteAdminByUserIdParams, Unit> {
    override suspend fun invoke(params: DeleteAdminByUserIdParams): Data<Unit> {
        return adminService.delete(params.adminId)
    }
}

data class DeleteAdminByUserIdParams(
    val adminId: String
)