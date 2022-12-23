package dev.december.jeterbackend.client.features.clients.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class ClientOtpFailure(
    override val code: Int = 401,
    override val message: String = "Wrong token!"
) : Failure
