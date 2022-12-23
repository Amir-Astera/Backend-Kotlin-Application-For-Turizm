package dev.december.jeterbackend.admin.features.faq.domain.services

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.faq.domain.models.FAQ


interface FAQService {
    suspend fun create(title: String, description: String, authority: PlatformRole): Data<String>
    suspend fun getAll(authority: PlatformRole): Data<List<FAQ>>
    suspend fun delete(id: String): Data<Unit>
    suspend fun deleteList(ids: List<String>): Data<Unit>
    suspend fun update(id: String, title: String?, description: String?): Data<String>
}