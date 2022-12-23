package dev.december.jeterbackend.client.features.faq.domain.services

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.faq.domain.models.FAQ

interface FAQService {
    suspend fun getAll(authority: PlatformRole): Data<List<FAQ>>
}