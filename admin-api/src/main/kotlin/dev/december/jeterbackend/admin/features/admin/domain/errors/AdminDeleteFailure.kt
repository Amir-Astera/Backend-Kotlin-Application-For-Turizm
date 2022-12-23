package dev.december.jeterbackend.admin.features.admin.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class AdminDeleteFailure(
    override val code: Int = 500,
    override val message: String = "Cannot delete admin!"
) : Failure
