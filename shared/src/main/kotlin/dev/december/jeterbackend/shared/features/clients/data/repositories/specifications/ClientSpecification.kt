package dev.december.jeterbackend.shared.features.clients.data.repositories.specifications

import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.OsType
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.time.LocalDateTime
import java.time.LocalTime

@Component
class ClientSpecification {
    companion object {
//        fun userJoinFilter(
//            searchField: String?,
//            createdFrom: LocalDateTime?,
//            createdTo: LocalDateTime?,
//        ): Specification<ClientEntity> {
//            return Specification<ClientEntity> { root, _, criteriaBuilder ->
//                val userJoin = root.join<ClientEntity, UserEntity>("user")
//                val predicates = mutableListOf<Predicate>()
//
//                if (searchField != null && StringUtils.hasText(searchField)) {
//                    val search = searchField.trim()
//                    val searchPredicates = mutableListOf<Predicate>()
//                    searchPredicates.add(
//                        criteriaBuilder.like(
//                            criteriaBuilder.lower(userJoin.get("fullName")),
//                            "%${search.lowercase()}%"
//                        )
//                    )
//                    predicates.add(criteriaBuilder.or(*searchPredicates.toTypedArray()))
//                }
//                if (createdFrom != null) {
//                    val from = createdFrom.toLocalDate().atStartOfDay().minusSeconds(1)
//                    predicates.add(criteriaBuilder.greaterThan(userJoin.get("createdAt"), from))
//                }
//                if (createdTo != null) {
//                    val to = createdTo.toLocalDate().atTime(LocalTime.MAX).plusSeconds(1)
//                    predicates.add(criteriaBuilder.lessThan(userJoin.get("createdAt"), to))
//                }
//                criteriaBuilder.and(*predicates.toTypedArray())
//            }
//        }

        fun containsFullName(fullName: String?): Specification<ClientEntity>? {
            return if (fullName != null && StringUtils.hasText(fullName))
                Specification<ClientEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%${fullName.lowercase()}%")
                } else null
        }

        fun isGreaterThanCreatedAt(createdFrom: LocalDateTime?): Specification<ClientEntity>? {
            return if (createdFrom != null) {
                val from = createdFrom.toLocalDate().atStartOfDay().minusSeconds(1)
                Specification<ClientEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.greaterThan(root.get("createdAt"), from)
                }
            } else null
        }

        fun isLessThanCreatedAt(createdTo: LocalDateTime?): Specification<ClientEntity>? {
            return if (createdTo != null) {
                val to = createdTo.toLocalDate().atTime(LocalTime.MAX).plusSeconds(1)
                Specification<ClientEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.lessThan(root.get("createdAt"), to)
                }
            } else null
        }

        fun hasEnableStatus(enableStatus: AccountEnableStatus?): Specification<ClientEntity>? {
            return if(enableStatus != null) {
                Specification<ClientEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.equal(root.get<AccountEnableStatus>("enableStatus"), enableStatus)
                }
            } else null
        }

        fun isInActivityStatus(activityStatuses: Set<AccountActivityStatus>?): Specification<ClientEntity>? {
            return if (activityStatuses != null && activityStatuses.isNotEmpty()) {
                Specification<ClientEntity> { root, _, _ ->
                    root.get<AccountActivityStatus>("activityStatus").`in`(activityStatuses)
                }
            } else null
        }

        fun isInOsType(osTypes: Set<OsType>?): Specification<ClientEntity>? {
            return if (osTypes != null && osTypes.isNotEmpty()) {
                Specification<ClientEntity> { root, _, _ ->
                    root.get<AccountActivityStatus>("osType").`in`(osTypes)
                }
            } else null
        }
    }

}