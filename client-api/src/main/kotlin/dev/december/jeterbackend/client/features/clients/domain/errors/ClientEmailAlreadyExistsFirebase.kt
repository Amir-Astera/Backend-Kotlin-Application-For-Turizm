package dev.december.jeterbackend.client.features.clients.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ClientEmailAlreadyExistsFirebase(
    val email: String,
    override val code: Int = 409,
    override val message: String = "Client email already exists in firebase!"
) : Failure