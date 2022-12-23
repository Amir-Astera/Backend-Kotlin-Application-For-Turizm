package dev.december.jeterbackend.shared.features.terrain.data.entities.extensions

import dev.december.jeterbackend.shared.features.terrain.data.entities.TerrainEntity
import dev.december.jeterbackend.shared.features.terrain.domain.models.Terrain
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import dev.december.jeterbackend.shared.features.files.domain.models.File

fun TerrainEntity.profession(): Terrain {
    return this.convert(
        mapOf(
            "file" to this.file?.convert<FileEntity, File>(),
        )
    )
}