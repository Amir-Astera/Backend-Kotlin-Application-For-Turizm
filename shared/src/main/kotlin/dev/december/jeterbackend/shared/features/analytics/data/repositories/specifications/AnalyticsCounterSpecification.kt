package dev.december.jeterbackend.shared.features.analytics.data.repositories.specifications

import dev.december.jeterbackend.shared.features.analytics.data.entities.AnalyticsCounterEntity
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AnalyticsCounterSpecification{
    companion object{
        fun isGreaterThanCreatedAt(updatedFrom: LocalDateTime?): Specification<AnalyticsCounterEntity> ?{
            return if (updatedFrom != null){
                val from = updatedFrom.toLocalDate().minusDays(1)
                Specification<AnalyticsCounterEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.greaterThan(root.get("createdAt"), from)
                }
            } else null
        }

        fun isLessThanCreatedAt(updatedTo: LocalDateTime?): Specification<AnalyticsCounterEntity>? {
            return if (updatedTo != null){
                val to = updatedTo.toLocalDate().plusDays(1)
                Specification<AnalyticsCounterEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.lessThan(root.get("createdAt"), to)
                }
            } else null
        }
    }
}