package dev.december.jeterbackend.client.features.clients.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ClientNotFoundInFirebaseFailure (
    override val code: Int = 400,
    override val message: String = "No client with this number found!"
) : Failure