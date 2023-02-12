package dev.december.jeterbackend.scheduler.features.client.domain.services

import dev.december.jeterbackend.shared.core.results.Data

interface ClientService {
    suspend fun delete(): Data<Unit>
}