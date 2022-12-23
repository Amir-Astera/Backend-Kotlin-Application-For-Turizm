package dev.december.jeterbackend.admin.features.analytics.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class AnalyticsTodayFailure(
    override val code: Int = 500,
    override val message: String = "Analytics Today Failure!"
) : Failure