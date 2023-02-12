package dev.december.jeterbackend.shared.features.chats.data.entities

import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "chat_support")
data class ChatSupportEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    val supplier: SupplierEntity? = null,
    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    val client: ClientEntity? = null,
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "chat_id")
    val messages: Set<MessageEntity> = emptySet(),
    @Enumerated(EnumType.STRING)
    @Column(name = "authority", nullable = false)
    val authority: PlatformRole,
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updatedAt", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)