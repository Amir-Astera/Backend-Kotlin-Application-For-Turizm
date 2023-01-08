package dev.december.jeterbackend.shared.features.calendar.data.entities

import com.vladmihalcea.hibernate.type.array.ListArrayType
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*


@Entity(name = "calendar")
@TypeDef(
    name = "list-array",
    typeClass = ListArrayType::class
)
data class CalendarEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @Column(name = "first_day_of_month")
    val firstDayOfMonth: LocalDate,
    @Type(type = "list-array")
    @Column(
        name = "working_days",
        columnDefinition = "date[]"
    )
    val workingDays: List<LocalDate>,
    @ManyToOne
    @JoinColumn(name="supplier_id", nullable=false)
    val supplier: SupplierEntity,
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)