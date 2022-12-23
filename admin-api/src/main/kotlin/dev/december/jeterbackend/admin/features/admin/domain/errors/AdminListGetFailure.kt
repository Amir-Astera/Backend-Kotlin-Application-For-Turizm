package dev.december.jeterbackend.admin.features.admin.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class AdminListGetFailure(
    override val code: Int = 500,
    override val message: String = "Cannot get admins!"
) : Failure
