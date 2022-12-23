package dev.december.jeterbackend.client.features.clients.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ClientCreateFailure(
    override val code: Int = 500,
    override val message: String = "Client create failure"
) : Failure
