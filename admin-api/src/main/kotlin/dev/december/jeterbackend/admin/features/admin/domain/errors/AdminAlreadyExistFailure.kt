package dev.december.jeterbackend.admin.features.admin.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class AdminAlreadyExistFailure(
    override val code: Int = 409,
    override val message: String = "Admin already exist"
) : Failure