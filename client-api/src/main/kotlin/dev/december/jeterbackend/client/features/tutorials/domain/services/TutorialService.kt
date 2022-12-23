package dev.december.jeterbackend.client.features.tutorials.domain.services

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.tutorials.domain.models.Tutorial

interface TutorialService {
    suspend fun getAll(authority: PlatformRole): Data<List<Tutorial>>
    suspend fun get(id: String): Data<Tutorial>
}