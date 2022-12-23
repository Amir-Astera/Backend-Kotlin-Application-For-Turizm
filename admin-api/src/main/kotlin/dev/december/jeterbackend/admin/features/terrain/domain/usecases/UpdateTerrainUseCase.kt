package dev.december.jeterbackend.admin.features.terrain.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.terrain.domain.services.TerrainService
import dev.december.jeterbackend.shared.features.files.domain.models.File
import org.springframework.stereotype.Component

@Component
class UpdateTerrainUseCase(
    private val terrainService: TerrainService,
) : UseCase<UpdateTerrainParams, Unit> {
    override suspend fun invoke(params: UpdateTerrainParams): Data<Unit> {
        return terrainService.update(params.id, params.title, params.priority, params.description, params.file)//params.specialistIds,
    }
}

data class UpdateTerrainParams(
    val id: String,
    val title: String?,
//    val specialistIds: Set<String>,
    val priority: Int?,
    val description: String?,
    val file: File?,
)