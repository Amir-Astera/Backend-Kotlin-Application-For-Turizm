package dev.december.jeterbackend.shared.features.suppliers.data.entiies

import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import org.hibernate.Hibernate
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity(name = "certificate")
data class CertificateEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @Column(name = "title")
    val title: String?,
    @Column(name = "issue_date")
    val issueDate: LocalDate?,
    @Column(name = "specialization")
    val specialization: String?,
    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    val file: FileEntity?  = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private val supplier: SupplierEntity? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CertificateEntity

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , title = $title , issueDate = $issueDate , specialization = $specialization , file = $file )"
    }
}