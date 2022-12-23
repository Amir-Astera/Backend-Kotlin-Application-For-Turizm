package dev.december.jeterbackend.shared.features.analytics.data.entities

import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity(name = "analytics_counter")
data class AnalyticsCounterEntity(
    @Id
    @Column(name = "id", nullable = false, length = 50)
    val id: String = UUID.randomUUID().toString(),
    @Column(name = "supplier_logins", nullable = false)
    val supplierLogins: Int = 0,
    @Column(name = "client_logins", nullable = false)
    val clientLogins: Int = 0,
    @Column(name = "total_logins", nullable = false)
    val totalLogins: Int = 0,
    @Column(name = "suppliers_created", nullable = false)
    val suppliersCreated: Int = 0,
    @Column(name = "clients_created", nullable = false)
    val clientsCreated: Int = 0,
    @Column(name = "total_created", nullable = false)
    val totalCreated: Int = 0,
    @Column(name = "tour_created", nullable = false)
    val tourCreated: Int = 0,
    @Column(name = "transactions_completed", nullable = false)
    val transactionsCompleted: Int = 0,
    @Column(name = "appointments_count", nullable = false)
    val appointmentsCount: Int = 0,
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDate = LocalDate.now(),
)