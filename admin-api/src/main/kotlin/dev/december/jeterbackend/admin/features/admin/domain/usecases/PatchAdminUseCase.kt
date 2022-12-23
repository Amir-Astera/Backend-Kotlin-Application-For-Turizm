package dev.december.jeterbackend.admin.features.admin.domain.usecases

import dev.december.jeterbackend.admin.features.admin.domain.services.AdminService
import dev.december.jeterbackend.admin.features.admin.presentation.dto.UpdateAdminData
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class PatchAdminUseCase(
    private val adminService: AdminService
): UseCase<PatchAdminParams, String> {
    override suspend fun invoke(params: PatchAdminParams): Data<String> {
        return adminService.update(
            params.id,
            params.updateAdminData
        )
    }
}

data class PatchAdminParams(
    val id: String,
    val updateAdminData: UpdateAdminData?,
)