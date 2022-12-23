package dev.december.jeterbackend.admin.features.admin.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class AdminAccessDeniedFailure(
    override val code: Int = 403,
    override val message: String = "Access denied"
) : Failure