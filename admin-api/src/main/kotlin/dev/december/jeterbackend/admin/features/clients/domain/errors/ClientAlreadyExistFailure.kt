package dev.december.jeterbackend.admin.features.clients.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ClientAlreadyExistFailure(
    override val code: Int = 500,
    override val message: String = "Client already exist"
) : Failure
