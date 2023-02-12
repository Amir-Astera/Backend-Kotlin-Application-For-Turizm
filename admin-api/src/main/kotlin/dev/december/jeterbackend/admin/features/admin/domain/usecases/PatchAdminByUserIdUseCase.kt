package dev.december.jeterbackend.admin.features.admin.domain.usecases

import dev.december.jeterbackend.admin.features.admin.domain.services.AdminService
import dev.december.jeterbackend.admin.features.admin.presentation.dto.UpdateAdminByUserIdData
import dev.december.jeterbackend.admin.features.admin.presentation.dto.UpdateAdminData
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class PatchAdminByUserIdUseCase(
    private val adminService: AdminService
): UseCase<PatchAdminByUserIdParams, String> {
    override suspend fun invoke(params: PatchAdminByUserIdParams): Data<String> {
        return adminService.updateById(
            params.adminId,
            params.updateAdminData
        )
    }
}

data class PatchAdminByUserIdParams(
    val adminId: String,
    val updateAdminData: UpdateAdminData?,
)