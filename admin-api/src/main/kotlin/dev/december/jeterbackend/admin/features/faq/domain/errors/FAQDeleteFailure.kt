package dev.december.jeterbackend.admin.features.faq.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class FAQDeleteFailure(
    override val code: Int = 500,
    override val message: String = "Cannot delete FAQ!"
) : Failure