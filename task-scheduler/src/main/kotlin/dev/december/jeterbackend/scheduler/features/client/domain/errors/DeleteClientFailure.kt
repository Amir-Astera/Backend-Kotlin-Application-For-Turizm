package dev.december.jeterbackend.scheduler.features.client.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class DeleteClientFailure(
    override val code: Int = 500,
    override val message: String = "Can not delete client"
): Failure