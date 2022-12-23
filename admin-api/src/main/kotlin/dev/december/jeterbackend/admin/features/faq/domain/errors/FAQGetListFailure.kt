package dev.december.jeterbackend.admin.features.faq.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class FAQGetListFailure (
    override val code: Int = 500,
    override val message: String = "Cannot get FAQ at this moment!"
) : Failure