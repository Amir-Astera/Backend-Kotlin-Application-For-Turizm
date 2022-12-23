package dev.december.jeterbackend.shared.features.appointments.data.repositories.specification

import dev.december.jeterbackend.shared.features.appointments.data.entities.AppointmentEntity
import dev.december.jeterbackend.shared.features.appointments.domain.models.AppointmentStatus
import dev.december.jeterbackend.shared.features.appointments.domain.models.CommunicationType
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.criteria.Predicate

@Component
class AppointmentSpecification {

    companion object {
        fun isInPeriod(
            createdFrom: LocalDateTime? = null,
            createdTo: LocalDateTime? = null
        ): Specification<AppointmentEntity> {
            return Specification<AppointmentEntity> { root, _, criteriaBuilder ->
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

        fun hasCommunicationType(communicationTypes: Set<CommunicationType>?): Specification<AppointmentEntity> {
            return Specification<AppointmentEntity> { root, _, criteriaBuilder ->
                val predicates = mutableListOf<Predicate>()
                if (communicationTypes != null && communicationTypes.isNotEmpty()) {
                    predicates.add(
                        root.get<CommunicationType>("communicationType").`in`(communicationTypes)
                    )
                }
                criteriaBuilder.and(*predicates.toTypedArray())
            }
        }

        fun hasStatus(statuses: Set<AppointmentStatus>?): Specification<AppointmentEntity>{
            return Specification<AppointmentEntity> { root, _, criteriaBuilder ->
                val predicates = mutableListOf<Predicate>()
                if (statuses != null && statuses.isNotEmpty()) {
                    predicates.add(
                        root.get<AppointmentStatus>("appointmentStatus").`in`(statuses)
                    )
                }
                criteriaBuilder.and(*predicates.toTypedArray())
            }
        }

        fun supplierJoinFilter(supplierId: String): Specification<AppointmentEntity> {
            return Specification<AppointmentEntity> { root, _, criteriaBuilder ->
                val supplierJoin = root.join<AppointmentEntity, SupplierEntity>("supplier")
                criteriaBuilder.equal(supplierJoin.get<String>("id"), supplierId)
            }
        }

        fun clientJoinFilter(clientId: String): Specification<AppointmentEntity> {
            return Specification<AppointmentEntity> { root, _, criteriaBuilder ->
                val clientJoin = root.join<AppointmentEntity, ClientEntity>("client")
                criteriaBuilder.equal(clientJoin.get<String>("id"), clientId)
            }
        }

        fun paymentIsNotNull(): Specification<AppointmentEntity> {
            return Specification<AppointmentEntity> { root, _, criteriaBuilder ->
                criteriaBuilder.isNotNull(root.get<Int?>("payment"))
            }
        }
    }

}