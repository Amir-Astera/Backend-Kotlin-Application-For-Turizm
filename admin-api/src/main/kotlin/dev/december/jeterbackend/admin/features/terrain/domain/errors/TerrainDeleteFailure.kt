package dev.december.jeterbackend.admin.features.terrain.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class TerrainDeleteFailure(
    override val code: Int = 500,
    override val message: String = "Cannot delete terrain!"
) : Failure