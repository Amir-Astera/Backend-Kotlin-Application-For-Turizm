package dev.december.jeterbackend.admin.features.terrain.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class TerrainNotFoundFailure(
    override val code: Int = 404,
    override val message: String = "Terrain not found!"
) : Failure
