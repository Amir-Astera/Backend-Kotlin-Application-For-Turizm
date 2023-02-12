package dev.december.jeterbackend.client.features.clients.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ClientUpdateLanguageFailure(
    override val code: Int = 500,
    override val message: String = "Cannot update client language!"
) : Failure
