package dev.december.jeterbackend.admin.features.faq.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class FAQNotFoundFailure(
    override val code: Int = 404,
    override val message: String = "FAQ not found!"
) : Failure