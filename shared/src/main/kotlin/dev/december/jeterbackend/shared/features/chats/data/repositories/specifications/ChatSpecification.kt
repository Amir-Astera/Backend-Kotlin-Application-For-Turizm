package dev.december.jeterbackend.shared.features.chats.data.repositories.specifications

import dev.december.jeterbackend.shared.features.chats.data.entities.ChatEntity
import dev.december.jeterbackend.shared.features.chats.data.entities.MessageEntity
import dev.december.jeterbackend.shared.features.chats.domain.models.ChatArchiveStatus
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import org.springframework.data.jpa.domain.Specification
import org.springframework.util.StringUtils
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.criteria.Predicate

class ChatSpecification {
    companion object {
        fun containsSupplierName(supplierName: String?): Specification<ChatEntity>? {
            return if (supplierName != null && StringUtils.hasText(supplierName))
                Specification<ChatEntity> { root, _, criteriaBuilder ->
                    val supplierGet = root.get<ChatEntity>("supplier")
                    criteriaBuilder.like(
                        criteriaBuilder.lower(supplierGet.get("name")),
                        "%${supplierName.lowercase()}%"
                    )
                } else null
        }

        fun containsClientFullName(clientFullName: String?): Specification<ChatEntity>? {
            return if (clientFullName != null && StringUtils.hasText(clientFullName))
                Specification<ChatEntity> { root, _, criteriaBuilder ->
                    val clientGet = root.get<ChatEntity>("client")
                    criteriaBuilder.like(criteriaBuilder.lower(clientGet.get("fullName")), "%${clientFullName.lowercase()}%")
                } else null
        }

        fun isGreaterThanCreatedAt(createdFrom: LocalDateTime?): Specification<ChatEntity>? {
            return if (createdFrom != null) {
                val from = createdFrom.toLocalDate().atStartOfDay().minusSeconds(1)
                Specification<ChatEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.greaterThan(root.get("createdAt"), from)
                }
            } else null
        }


        fun isLessThanCreatedAt(createdTo: LocalDateTime?): Specification<ChatEntity>? {
            return if (createdTo != null) {
                val to = createdTo.toLocalDate().atTime(LocalTime.MAX).plusSeconds(1)
                Specification<ChatEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.lessThan(root.get("createdAt"), to)
                }
            } else null
        }

        fun isArchived(archive: ChatArchiveStatus?): Specification<ChatEntity>? {
            return if (archive != null) {
                Specification<ChatEntity> { root, _, _ ->
                    root.get<ChatArchiveStatus>("archiveStatus").`in`(archive)
                }
            } else null
        }

        fun findAllByClientId(client: ClientEntity): Specification<ChatEntity> {
            return Specification<ChatEntity> { root, _, criteriaBuilder ->
                val clientGet = root.get<ChatEntity>("client")
                criteriaBuilder.like(clientGet.get("id"), client.id)
            }
        }

        fun findAllBySupplierId(supplier: SupplierEntity): Specification<ChatEntity> {
            return Specification<ChatEntity> { root, _, criteriaBuilder ->
                val predicate = mutableListOf<Predicate>()
                val supplierGet = root.get<ChatEntity>("supplier")
                predicate.add(supplierGet.get<String>("id").`in`(supplier.id))
                criteriaBuilder.and(*predicate.toTypedArray())
            }
        }

        fun containsMessage(message: String?): Specification<MessageEntity>? {
            return if (message != null && StringUtils.hasText(message)) {
                Specification<MessageEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), "%${message.lowercase()}%")
                }
            } else null
        }

        fun findAllByChatId(chat: ChatEntity): Specification<MessageEntity> {
            return Specification<MessageEntity> { root, _, criteriaBuilder ->
                val predicate = mutableListOf<Predicate>()
                val chatGet = root.get<MessageEntity>("chat")
                predicate.add(chatGet.get<String>("id").`in`(chat.id))
                criteriaBuilder.and(*predicate.toTypedArray())
            }
        }

    }
}