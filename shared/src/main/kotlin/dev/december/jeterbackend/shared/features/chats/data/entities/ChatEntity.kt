package dev.december.jeterbackend.shared.features.chats.data.entities

import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.features.chats.domain.models.ChatArchiveStatus
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "chat")
data class ChatEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = false)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    val client: ClientEntity,
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = false)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    val supplier: SupplierEntity,
    @Enumerated(EnumType.STRING)
    @Column(name = "archive_status", nullable = false)
    val archiveStatus: ChatArchiveStatus = ChatArchiveStatus.UNARCHIVED,
    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "chat_id")
    val messages: Set<MessageEntity>? = emptySet(),
    @Column(name = "unread_messages_count", nullable = false)
    val unreadMessagesCount: Int = 0,
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updatedAt", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
{
    override fun hashCode(): Int = id.hashCode()

}