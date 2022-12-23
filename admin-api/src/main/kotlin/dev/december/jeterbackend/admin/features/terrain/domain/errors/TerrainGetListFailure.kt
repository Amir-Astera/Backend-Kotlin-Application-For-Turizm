package dev.december.jeterbackend.admin.features.terrain.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class TerrainGetListFailure(
    override val code: Int = 500,
    override val message: String = "Cannot get terrain at this moment!"
) : Failure