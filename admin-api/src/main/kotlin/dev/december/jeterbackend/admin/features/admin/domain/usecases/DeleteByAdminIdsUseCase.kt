package dev.december.jeterbackend.admin.features.admin.domain.usecases

import dev.december.jeterbackend.admin.features.admin.domain.services.AdminService
import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import org.springframework.stereotype.Component

@Component
class DeleteByAdminIdsUseCase(
    private val adminService: AdminService
) : UseCase<DeleteByAdminIdsParams, Unit> {
    override suspend fun invoke(params: DeleteByAdminIdsParams): Data<Unit> {
        return adminService.deleteList(params.ids)
    }
}

data class DeleteByAdminIdsParams(
    val ids: Set<String>
)