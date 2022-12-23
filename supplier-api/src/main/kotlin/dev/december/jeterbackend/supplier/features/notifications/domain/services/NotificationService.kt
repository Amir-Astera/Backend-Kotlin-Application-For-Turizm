package dev.december.jeterbackend.supplier.features.notifications.domain.services

import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import java.time.LocalDateTime

interface NotificationService {
    suspend fun sendSoonAppointmentNotifications(): Data<Unit>;
    suspend fun cancelNotification(
        client: ClientEntity,
        supplier: SupplierEntity,
        reservationDate: LocalDateTime,
    ): Data<Unit>

    suspend fun clearNotifications()
    suspend fun confirmNotification(
        supplier: SupplierEntity,
        client: ClientEntity,
        reservationDate: LocalDateTime
    ): Data<Unit>

    suspend fun changeTimeNotification(
        client: ClientEntity,
        supplier: SupplierEntity,
        reservationDate: LocalDateTime,
    ): Data<Unit>



}