package dev.december.jeterbackend.shared.features.feedbacks.data.entities

import dev.december.jeterbackend.shared.features.feedbacks.domain.models.FeedbackStatus
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "feedback")
data class FeedbackEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @Column(name = "grade", nullable = false)
    val grade: Float,
    @Column(name = "description")
    val description: String = "",
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: FeedbackStatus = FeedbackStatus.UNDEFINED,
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    val client: ClientEntity,
    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    val supplier: SupplierEntity,
    @Column(name = "createdAt", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    override fun toString(): String {
        return "FeedbackEntity(id='$id', grade=$grade, description='$description', status=$status, client=$client, supplier=$supplier, createdAt=$createdAt)"
    }
}