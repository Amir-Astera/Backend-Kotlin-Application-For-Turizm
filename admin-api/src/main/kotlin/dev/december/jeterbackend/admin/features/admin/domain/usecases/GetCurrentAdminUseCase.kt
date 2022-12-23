package dev.december.jeterbackend.admin.features.admin.domain.usecases

import dev.december.jeterbackend.admin.features.admin.domain.services.AdminService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.admin.domain.models.Admin
import org.springframework.stereotype.Component

@Component
class GetCurrentAdminUseCase(
    private val adminService: AdminService,
) : UseCase<GetCurrentAdminParams, Admin> {
    override suspend fun invoke(params: GetCurrentAdminParams): Data<Admin> {
        return adminService.getByUserId(params.userId)
    }

}

data class GetCurrentAdminParams(
    val userId: String
)