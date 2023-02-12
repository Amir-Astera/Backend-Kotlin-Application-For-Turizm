package dev.december.jeterbackend.scheduler.features.supplier.domain.services

import dev.december.jeterbackend.shared.core.results.Data

interface SupplierService {
    suspend fun resetActivityStatus(): Data<Unit>
    suspend fun delete(): Data<Unit>
}