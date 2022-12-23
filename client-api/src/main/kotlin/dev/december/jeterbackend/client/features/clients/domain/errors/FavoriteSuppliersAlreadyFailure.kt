package dev.december.jeterbackend.client.features.clients.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class FavoriteSuppliersAlreadyFailure(
    override val code: Int = 409,
    override val message: String = "Supplier is already favorite!"
) : Failure