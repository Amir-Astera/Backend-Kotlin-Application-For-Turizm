package dev.december.jeterbackend.client.features.clients.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class FavoriteSuppliersNotFoundFailure(
    override val code: Int = 404,
    override val message: String = "Favorite supplier not found!"
) : Failure