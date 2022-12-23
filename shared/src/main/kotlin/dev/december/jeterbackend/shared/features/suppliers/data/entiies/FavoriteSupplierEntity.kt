package dev.december.jeterbackend.shared.features.suppliers.data.entiies

import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity(name = "favorite_supplier")
data class FavoriteSupplierEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(), @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="favorite_supplier_id") val supplier: SupplierEntity,
    @ManyToOne()
    @JoinColumn(name="client_id")
    private val client: ClientEntity?=null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as FavoriteSupplierEntity

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , supplier = $supplier , client = $client )"
    }
}