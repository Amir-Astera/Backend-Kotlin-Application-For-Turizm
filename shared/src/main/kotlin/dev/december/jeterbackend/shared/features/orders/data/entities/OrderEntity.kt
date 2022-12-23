package dev.december.jeterbackend.shared.features.orders.data.entities

import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "orders")
data class OrderEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @Column(name = "invoice_id")
    val invoiceId: String,
    @Column(name = "amount")
    val amount: Int,
    @Column(name = "description")
    val description: String,
    @Column(name = "transaction_id")
    val transactionId: Int,
    @Column(name = "token")
    val token: String,
    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    val clientId: ClientEntity,
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
