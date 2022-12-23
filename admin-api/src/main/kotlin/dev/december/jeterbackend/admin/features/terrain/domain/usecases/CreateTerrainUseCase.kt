package dev.december.jeterbackend.admin.features.terrain.domain.usecases

import dev.december.jeterbackend.shared.core.domain.usecases.UseCase
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.admin.features.terrain.domain.services.TerrainService
import dev.december.jeterbackend.shared.features.files.domain.models.File
import org.springframework.stereotype.Component

@Component
class CreateTerrainUseCase(
    private val terrainService: TerrainService
): UseCase<CreateTerrainParams, String> {
    override suspend fun invoke(params: CreateTerrainParams): Data<String> {
        return terrainService.create(params.title, params.specialities,
            params.priority, params.description, params.file)
    }
}

data class CreateTerrainParams(
    val title: String,
    val specialities: List<String>?,
    val priority: Int,
    val description: String,
    val file: File?
)