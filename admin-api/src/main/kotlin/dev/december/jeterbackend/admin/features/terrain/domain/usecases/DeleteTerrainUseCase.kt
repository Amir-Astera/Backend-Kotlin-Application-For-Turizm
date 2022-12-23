package dev.december.jeterbackend.admin.features.terrain.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.terrain.domain.services.TerrainService
import org.springframework.stereotype.Component

@Component
class DeleteTerrainUseCase(
    private val terrainService: TerrainService
): UseCase<DeleteTerrainParams, Unit> {
    override suspend fun invoke(params: DeleteTerrainParams): Data<Unit> {
        return terrainService.delete(params.id)
    }
}

data class DeleteTerrainParams(
    val id: String
)