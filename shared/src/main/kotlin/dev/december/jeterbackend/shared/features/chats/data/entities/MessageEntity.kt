package dev.december.jeterbackend.shared.features.chats.data.entities

import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.features.chats.domain.models.MessageStatus
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import org.hibernate.Hibernate
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.*

@Entity(name = "messages")
data class MessageEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    val supplier: SupplierEntity? = null,
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    val client: ClientEntity? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    val chat: ChatEntity,
    @Column(name = "content")
    val content: String,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinTable(
        name = "message_files",
        joinColumns = [JoinColumn(name = "message_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "file_id", referencedColumnName = "id")]
    )
    val files: Set<FileEntity> = emptySet(),
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    val status: MessageStatus,
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as MessageEntity

        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}

