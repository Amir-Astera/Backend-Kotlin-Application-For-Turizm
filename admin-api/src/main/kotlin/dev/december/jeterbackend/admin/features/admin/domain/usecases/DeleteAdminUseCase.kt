package dev.december.jeterbackend.admin.features.admin.domain.usecases

import dev.december.jeterbackend.admin.features.admin.domain.services.AdminService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class DeleteAdminUseCase(
    private val adminService: AdminService
) : UseCase<DeleteAdminParams, Unit> {
    override suspend fun invoke(params: DeleteAdminParams): Data<Unit> {
        return adminService.delete(params.id)
    }
}

data class DeleteAdminParams(
    val id: String
)