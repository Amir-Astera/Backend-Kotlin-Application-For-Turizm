package dev.december.jeterbackend.shared.features.chats.data.repositories.specifications

import dev.december.jeterbackend.shared.features.chats.data.entities.ChatSupportEntity
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.LocalTime

@Component
class ChatSupportSpecification {
    companion object {

        fun isGreaterThanCreatedAt(createdFrom: LocalDateTime?): Specification<ChatSupportEntity>? {
            return if (createdFrom != null) {
                val from = createdFrom.toLocalDate().atStartOfDay().minusSeconds(1)
                Specification<ChatSupportEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.greaterThan(root.get("createdAt"), from)
                }
            } else null
        }

        fun isLessThanCreatedAt(createdTo: LocalDateTime?): Specification<ChatSupportEntity>? {
            return if (createdTo != null) {
                val to = createdTo.toLocalDate().atTime(LocalTime.MAX).plusSeconds(1)
                Specification<ChatSupportEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.lessThan(root.get("createdAt"), to)
                }
            } else null
        }
    }
}