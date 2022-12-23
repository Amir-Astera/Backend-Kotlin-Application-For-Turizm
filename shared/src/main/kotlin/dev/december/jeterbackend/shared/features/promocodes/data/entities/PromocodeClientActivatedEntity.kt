package dev.december.jeterbackend.shared.features.promocodes.data.entities

import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity(name = "promocode_client_activated")
data class PromocodeClientActivatedEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="promocode_id")
    val promocode: PromocodeEntity,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    val client: ClientEntity,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as PromocodeClientActivatedEntity

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , promocode = $promocode , client = ${client.id} )"
    }
}