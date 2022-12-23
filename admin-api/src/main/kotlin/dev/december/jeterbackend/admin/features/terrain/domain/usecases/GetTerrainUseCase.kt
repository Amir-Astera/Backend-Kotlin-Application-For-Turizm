package dev.december.jeterbackend.admin.features.terrain.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.terrain.domain.services.TerrainService
import dev.december.jeterbackend.shared.features.terrain.domain.models.Terrain
import org.springframework.stereotype.Component

@Component
class GetTerrainUseCase(
    private val terrainService: TerrainService
) : UseCase<GetTerrainParams, Terrain> {
    override suspend fun invoke(params: GetTerrainParams): Data<Terrain> {
        return terrainService.get(params.id)
    }
}

data class GetTerrainParams(
    val id: String
)