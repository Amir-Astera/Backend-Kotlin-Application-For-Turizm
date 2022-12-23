package dev.december.jeterbackend.admin.features.terrain.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class TerrainUpdateFailure(
    override val code: Int = 500,
    override val message: String = "Couldn't update profession!",
): Failure
