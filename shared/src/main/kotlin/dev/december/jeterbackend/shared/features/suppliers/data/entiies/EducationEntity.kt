package dev.december.jeterbackend.shared.features.suppliers.data.entiies

import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity(name = "education")
data class EducationEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @Column(name = "institution_name")
    val institutionName: String?,
    @Column(name = "year")
    val graduationYear: Int?,
    @Column(name = "faculty")
    val faculty: String?,
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
        other as EducationEntity

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , institutionName = $institutionName , graduationYear = $graduationYear , faculty = $faculty , file = $file )"
    }
}