package dev.december.jeterbackend.shared.features.feedbacks.data.repositories.specifications

import dev.december.jeterbackend.shared.features.feedbacks.data.entities.FeedbackEntity
import dev.december.jeterbackend.shared.features.feedbacks.domain.models.FeedbackStatus
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.LocalTime

@Component
class FeedbackSpecification {
    companion object {
        fun isInStatus(statuses: Set<FeedbackStatus>): Specification<FeedbackEntity> {
            return Specification<FeedbackEntity> { root, _, _ ->
                root.get<FeedbackStatus>("status").`in`(statuses)
            }
        }

        fun isGreaterThanCreatedAt(createdFrom: LocalDateTime?): Specification<FeedbackEntity>? {
            return if (createdFrom != null) {
                val from = createdFrom.toLocalDate().atStartOfDay().minusSeconds(1)
                Specification<FeedbackEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.greaterThan(root.get("createdAt"), from)
                }
            } else null
        }

        fun isLessThanCreatedAt(createdTo: LocalDateTime?): Specification<FeedbackEntity>? {
            return if (createdTo != null) {
                val to = createdTo.toLocalDate().atTime(LocalTime.MAX).plusSeconds(1)
                Specification<FeedbackEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.lessThan(root.get("createdAt"), to)
                }
            } else null
        }

        fun isInGrade(grades: Set<Float>?): Specification<FeedbackEntity>? {
            return if (grades != null) {
                Specification<FeedbackEntity> { root, _, _ ->
                    root.get<Float>("grade").`in`(grades)
                }
            } else null
        }

        fun supplierJoinFilter(supplierId: String): Specification<FeedbackEntity> {
            return Specification<FeedbackEntity> { root, _, criteriaBuilder ->
                val supplierJoin = root.join<FeedbackEntity, SupplierEntity>("supplier")
                criteriaBuilder.equal(supplierJoin.get<String>("id"), supplierId)
            }
        }

        fun clientJoinFilter(clientId: String): Specification<FeedbackEntity> {
            return Specification<FeedbackEntity> { root, _, criteriaBuilder ->
                val clientJoin = root.join<FeedbackEntity, ClientEntity>("client")
                criteriaBuilder.equal(clientJoin.get<String>("id"), clientId)
            }
        }
    }
}