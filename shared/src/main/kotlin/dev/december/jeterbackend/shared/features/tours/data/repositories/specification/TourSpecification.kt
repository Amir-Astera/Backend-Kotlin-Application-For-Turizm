package dev.december.jeterbackend.shared.features.tours.data.repositories.specification

import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.tours.data.entities.TourEntity
import dev.december.jeterbackend.shared.features.tours.domain.models.TourStatus
import dev.december.jeterbackend.shared.features.tours.domain.models.CommunicationType
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.criteria.Predicate

@Component
class TourSpecification {

    companion object {
        fun isInPeriod(
            createdFrom: LocalDateTime? = null,
            createdTo: LocalDateTime? = null
        ): Specification<TourEntity> {
            return Specification<TourEntity> { root, _, criteriaBuilder ->
                val predicates = mutableListOf<Predicate>()
                if (createdFrom != null) {
                    val from = createdFrom.toLocalDate().atStartOfDay().minusSeconds(1)
                    predicates.add(criteriaBuilder.greaterThan(root.get("reservationDate"), from))
                }
                if (createdTo != null) {
                    val to = createdTo.toLocalDate().atTime(LocalTime.MAX).plusSeconds(1)
                    predicates.add(criteriaBuilder.lessThan(root.get("reservationDate"), to))
                }
                criteriaBuilder.and(*predicates.toTypedArray())
            }
        }

        fun hasCommunicationType(communicationTypes: Set<CommunicationType>?): Specification<TourEntity> {
            return Specification<TourEntity> { root, _, criteriaBuilder ->
                val predicates = mutableListOf<Predicate>()
                if (communicationTypes != null && communicationTypes.isNotEmpty()) {
                    predicates.add(
                        root.get<CommunicationType>("communicationType").`in`(communicationTypes)
                    )
                }
                criteriaBuilder.and(*predicates.toTypedArray())
            }
        }

        fun hasStatus(statuses: Set<TourStatus>?): Specification<TourEntity>{
            return Specification<TourEntity> { root, _, criteriaBuilder ->
                val predicates = mutableListOf<Predicate>()
                if (statuses != null && statuses.isNotEmpty()) {
                    predicates.add(
                        root.get<TourStatus>("appointmentStatus").`in`(statuses)
                    )
                }
                criteriaBuilder.and(*predicates.toTypedArray())
            }
        }

        fun supplierJoinFilter(supplierId: String): Specification<TourEntity> {
            return Specification<TourEntity> { root, _, criteriaBuilder ->
                val supplierJoin = root.join<TourEntity, SupplierEntity>("supplier")
                criteriaBuilder.equal(supplierJoin.get<String>("id"), supplierId)
            }
        }

        fun clientJoinFilter(clientId: String): Specification<TourEntity> {
            return Specification<TourEntity> { root, _, criteriaBuilder ->
                val clientJoin = root.join<TourEntity, ClientEntity>("client")
                criteriaBuilder.equal(clientJoin.get<String>("id"), clientId)
            }
        }

        fun paymentIsNotNull(): Specification<TourEntity> {
            return Specification<TourEntity> { root, _, criteriaBuilder ->
                criteriaBuilder.isNotNull(root.get<Int?>("payment"))
            }
        }
    }

}