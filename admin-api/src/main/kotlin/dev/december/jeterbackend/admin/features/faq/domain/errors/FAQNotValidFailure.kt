package dev.december.jeterbackend.admin.features.faq.domain.errors

import dev.december.jeterbackend.shared.core.errors.Failure

data class FAQNotValidFailure(
    override val code: Int = 400,
    override val message: String = "Invalid request - name and description are blank!"
) : Failure