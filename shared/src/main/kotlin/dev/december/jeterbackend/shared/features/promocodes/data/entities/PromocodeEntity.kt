package dev.december.jeterbackend.shared.features.promocodes.data.entities

import dev.december.jeterbackend.shared.features.promocodes.domain.models.PromocodeDiscountType
import dev.december.jeterbackend.shared.features.promocodes.domain.models.PromocodeStatus
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.features.admin.data.entities.AdminEntity
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity(name = "promocode")
data class PromocodeEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @Column(name = "code", nullable = false, unique = true)
    val code: String,
    @Column(name = "description")
    val description: String? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    val discountType: PromocodeDiscountType,
    @Column(name = "discount_amount")
    val discountAmount: Int?,
    @Column(name = "discount_percentage")
    val discountPercentage: Double?,
    @Column(name = "activation_limit", nullable = false)
    val activationLimit: Int = 1,
    @Column(name = "activated_amount", nullable = false)
    val activatedAmount: Int = 0,
    @Column(name = "total_attempts", nullable = false)
    val totalAttempts: Int = 0,
    @Column(name = "validity_from", nullable = false)
    val validityFrom: LocalDateTime,
    @Column(name = "validity_to", nullable = false)
    val validityTo: LocalDateTime,
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: PromocodeStatus,
    @ManyToOne
    @JoinColumn(name = "admin_id")
    val admin: AdminEntity? = null,
    @ManyToOne
    @JoinColumn(name = "supplier_id")
    val supplier: SupplierEntity?,
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
