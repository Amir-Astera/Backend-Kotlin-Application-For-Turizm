package dev.december.jeterbackend.client.features.notifications.domain.services

import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import java.time.LocalDateTime

interface NotificationService {

    suspend fun create(
        client: ClientEntity,
        supplier: SupplierEntity,
        reservationDate: LocalDateTime
    ): Data<Unit>

    suspend fun cancel(
        client: ClientEntity,
        supplier: SupplierEntity,
        reservationDate: LocalDateTime,
    ): Data<Unit>

    suspend fun confirm(
        supplier: SupplierEntity,
        client: ClientEntity,
        reservationDate: LocalDateTime
    ): Data<Unit>

    suspend fun changeTime(
        client: ClientEntity,
        supplier: SupplierEntity,
        reservationDate: LocalDateTime,
        oldReservationDate: LocalDateTime,
    ): Data<Unit>



}