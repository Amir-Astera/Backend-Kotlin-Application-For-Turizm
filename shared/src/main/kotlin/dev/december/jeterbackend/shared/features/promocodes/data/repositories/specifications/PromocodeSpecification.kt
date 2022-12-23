package dev.december.jeterbackend.shared.features.promocodes.data.repositories.specifications

import dev.december.jeterbackend.shared.features.promocodes.data.entities.PromocodeEntity
import dev.december.jeterbackend.shared.features.promocodes.domain.models.PromocodeDiscountType
import dev.december.jeterbackend.shared.features.promocodes.domain.models.PromocodeStatus
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.features.admin.data.entities.AdminEntity
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.criteria.Predicate

@Component
class PromocodeSpecification {

    companion object {

        fun hasDiscountType(discountTypes: Set<PromocodeDiscountType>?): Specification<PromocodeEntity> {
            return Specification<PromocodeEntity> { root, _, criteriaBuilder ->
                val predicates = mutableListOf<Predicate>()
                if (discountTypes != null && discountTypes.isNotEmpty()) {
                    predicates.add(
                        root.get<PromocodeDiscountType>("discountType").`in`(discountTypes)
                    )
                }
                criteriaBuilder.and(*predicates.toTypedArray())
            }
        }

        fun hasStatus(statuses: Set<PromocodeStatus>?): Specification<PromocodeEntity>{
            return Specification<PromocodeEntity> { root, _, criteriaBuilder ->
                val predicates = mutableListOf<Predicate>()
                if (statuses != null && statuses.isNotEmpty()) {
                    predicates.add(
                        root.get<PromocodeStatus>("status").`in`(statuses)
                    )
                }
                criteriaBuilder.and(*predicates.toTypedArray())
            }
        }

        fun supplierJoinFilter(supplierId: String): Specification<PromocodeEntity> {
            return Specification<PromocodeEntity> { root, _, criteriaBuilder ->
                val supplierJoin = root.join<PromocodeEntity, SupplierEntity>("supplier")
                criteriaBuilder.equal(supplierJoin.get<String>("id"), supplierId)
            }
        }

        fun adminJoinFilter(clientId: String): Specification<PromocodeEntity> {
            return Specification<PromocodeEntity> { root, _, criteriaBuilder ->
                val clientJoin = root.join<PromocodeEntity, AdminEntity>("admin")
                criteriaBuilder.equal(clientJoin.get<String>("id"), clientId)
            }
        }

        fun containsCode(code: String?): Specification<PromocodeEntity>? {
            return if (code != null && StringUtils.hasText(code))
                Specification<PromocodeEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("code")), "%${code.lowercase()}%")
                } else null
        }

        fun isGreaterThanCreatedAt(createdFrom: LocalDateTime?): Specification<PromocodeEntity>? {
            return if (createdFrom != null) {
                val from = createdFrom.toLocalDate().atStartOfDay().minusSeconds(1)
                Specification<PromocodeEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.greaterThan(root.get("createdAt"), from)
                }
            } else null
        }

        fun isLessThanCreatedAt(createdTo: LocalDateTime?): Specification<PromocodeEntity>? {
            return if (createdTo != null) {
                val to = createdTo.toLocalDate().atTime(LocalTime.MAX).plusSeconds(1)
                Specification<PromocodeEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.lessThan(root.get("createdAt"), to)
                }
            } else null
        }

        fun isValid(validDate: LocalDateTime?): Specification<PromocodeEntity>? {
            return if (validDate != null) {
                val date = validDate.toLocalDate().atStartOfDay().minusSeconds(1)
                Specification<PromocodeEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.between(criteriaBuilder.literal(date), root.get("validityFrom"), root.get("validityTo"))
                }
            } else null
        }

        fun isInPeriod(
            validFrom: LocalDateTime? = null,
            validTo: LocalDateTime? = null
        ): Specification<PromocodeEntity> {
            return Specification<PromocodeEntity> { root, _, criteriaBuilder ->
                val predicates = mutableListOf<Predicate>()
                if (validFrom != null) {
                    val from = validFrom.toLocalDate().atStartOfDay().minusSeconds(1)
                    predicates.add(criteriaBuilder.greaterThan(root.get("validityTo"), from))
                }
                if (validTo != null) {
                    val to = validTo.toLocalDate().atTime(LocalTime.MAX).plusSeconds(1)
                    predicates.add(criteriaBuilder.lessThan(root.get("validityTo"), to))
                }
                criteriaBuilder.and(*predicates.toTypedArray())
            }
        }

        fun isExpired(
            currentDate: LocalDateTime
        ): Specification<PromocodeEntity> {
            val from = currentDate.toLocalDate().atStartOfDay()
            return Specification<PromocodeEntity> { root, _, criteriaBuilder ->
                criteriaBuilder.lessThan(root.get("validityTo"), from)
            }
        }
    }
}