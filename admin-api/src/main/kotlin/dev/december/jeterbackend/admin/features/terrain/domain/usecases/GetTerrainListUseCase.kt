package dev.december.jeterbackend.admin.features.terrain.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.terrain.domain.services.TerrainService
import dev.december.jeterbackend.shared.features.terrain.domain.models.Terrain
import org.springframework.stereotype.Component

@Component
class GetTerrainListUseCase(
    private val terrainService: TerrainService
) : UseCase<Unit, List<Terrain>> {
    override suspend fun invoke(params: Unit): Data<List<Terrain>> {
        return terrainService.getAll()
    }
}