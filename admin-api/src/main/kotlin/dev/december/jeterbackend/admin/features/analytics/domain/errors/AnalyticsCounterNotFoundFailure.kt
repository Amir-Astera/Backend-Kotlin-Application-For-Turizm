package dev.december.jeterbackend.admin.features.analytics.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class AnalyticsCounterNotFoundFailure(
    override val code: Int = 404,
    override val message: String = "Analytics Counter not found!"
) : Failure