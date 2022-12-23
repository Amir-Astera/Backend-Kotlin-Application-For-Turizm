package dev.december.jeterbackend.admin.features.clients.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ClientNotFoundFailure(
    override val code: Int = 404,
    override val message: String = "Client not found!"
) : Failure
