package dev.december.jeterbackend.shared.features.admin.data.repositories.specifications

import dev.december.jeterbackend.shared.features.admin.data.entities.AdminEntity
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.features.admin.data.entities.extensions.AdminAuthorityEntity
import dev.december.jeterbackend.shared.features.admin.domain.models.AdminAuthorityCode
import dev.december.jeterbackend.shared.features.authorities.data.entities.UserAuthorityEntity
import dev.december.jeterbackend.shared.features.authorities.domain.models.AuthorityCode
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.criteria.Predicate
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus as AccountEnableStatus1

@Component
class AdminSpecification {
    companion object {
        fun adminAuthorityJoinFilter(
            authorityCodes: Set<AdminAuthorityCode>?,
        ): Specification<AdminEntity>? {
            return if (authorityCodes != null) {
                Specification<AdminEntity> { root, _, criteriaBuilder ->
                    val predicates = mutableListOf<Predicate>()
                    val adminAuthorityJoin = root.join<AdminEntity, AdminAuthorityEntity>("adminAuthorities")
                    val convertedAuthorityCodes = authorityCodes.map { it.convertToAuthorityCode() }
                    predicates.add(adminAuthorityJoin.get<AuthorityCode>("authority").`in`(convertedAuthorityCodes))
                    criteriaBuilder.and(*predicates.toTypedArray())
                }
            }  else null
        }

        fun containsFullName(fullName: String?): Specification<AdminEntity>? {
            return if (fullName != null && StringUtils.hasText(fullName))
                Specification<AdminEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%${fullName.lowercase()}%")
                } else null
        }

        fun isGreaterThanCreatedAt(createdFrom: LocalDateTime?): Specification<AdminEntity>? {
            return if (createdFrom != null) {
                val from = createdFrom.toLocalDate().atStartOfDay().minusSeconds(1)
                Specification<AdminEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.greaterThan(root.get("createdAt"), from)
                }
            } else null
        }

        fun isLessThanCreatedAt(createdTo: LocalDateTime?): Specification<AdminEntity>? {
            return if (createdTo != null) {
                val to = createdTo.toLocalDate().atTime(LocalTime.MAX).plusSeconds(1)
                Specification<AdminEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.lessThan(root.get("createdAt"), to)
                }
            } else null
        }

        fun hasEnableStatus(enableStatus: AccountEnableStatus1?): Specification<AdminEntity>? {
            return if(enableStatus != null) {
                Specification<AdminEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.equal(root.get<AccountEnableStatus1>("enableStatus"), enableStatus)
                }
            } else null
        }

        fun isInActivityStatus(activityStatuses: Set<AccountActivityStatus>?): Specification<AdminEntity>? {
            return if (activityStatuses != null && activityStatuses.isNotEmpty()) {
                Specification<AdminEntity> { root, _, _ ->
                    root.get<AccountActivityStatus>("activityStatus").`in`(activityStatuses)
                }
            } else null
        }
    }
}