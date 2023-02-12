package dev.december.jeterbackend.shared.features.chats.data.repositories.specifications

import dev.december.jeterbackend.shared.features.chats.data.entities.ChatSupportEntity
import dev.december.jeterbackend.shared.features.chats.data.entities.SupportMessageEntity
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.criteria.Predicate

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

        fun containsMessage(message: String?): Specification<SupportMessageEntity>? {
            return if (message != null && StringUtils.hasText(message)) {
                Specification<SupportMessageEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), "%${message.lowercase()}%")
                }
            } else null
        }

        fun findAllByChatId(chat: ChatSupportEntity): Specification<SupportMessageEntity> {
            return Specification<SupportMessageEntity> { root, _, criteriaBuilder ->
                val predicate = mutableListOf<Predicate>()
                val chatGet = root.get<SupportMessageEntity>("chat")
                predicate.add(chatGet.get<String>("id").`in`(chat.id))
                criteriaBuilder.and(*predicate.toTypedArray())
            }
        }
    }
}