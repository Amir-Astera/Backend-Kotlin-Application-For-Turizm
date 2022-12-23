package dev.december.jeterbackend.admin.features.admin.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class AdminNotFoundFailure(
    override val code: Int = 404,
    override val message: String = "Admin not found!"
) : Failure
