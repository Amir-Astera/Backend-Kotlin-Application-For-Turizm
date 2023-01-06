package dev.december.jeterbackend.shared.features.suppliers.data.repositories.specifications

import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.Gender
import dev.december.jeterbackend.shared.core.domain.model.OsType
import dev.december.jeterbackend.shared.features.authorities.data.entities.UserAuthorityEntity
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.time.LocalDateTime
import java.time.LocalTime
import javax.persistence.criteria.Predicate

@Component
class SupplierSpecification {
    companion object {
        fun userJoinFilter(
            searchField: String?,
            createdFrom: LocalDateTime?,
            createdTo: LocalDateTime?,
        ): Specification<SupplierEntity> {
            return Specification<SupplierEntity> { root, _, criteriaBuilder ->
                val userJoin = root.join<SupplierEntity, UserAuthorityEntity>("userAuthorities")
                val predicates = mutableListOf<Predicate>()

                if (searchField != null && StringUtils.hasText(searchField)) {
                    val search = searchField.trim()
                    val searchPredicates = mutableListOf<Predicate>()
                    searchPredicates.add(
                        criteriaBuilder.like(
                            criteriaBuilder.lower(userJoin.get("fullName")),
                            "%${search.lowercase()}%"
                        )
                    )
                    predicates.add(criteriaBuilder.or(*searchPredicates.toTypedArray()))
                }
                if (createdFrom != null) {
                    val from = createdFrom.toLocalDate().atStartOfDay().minusSeconds(1)
                    predicates.add(criteriaBuilder.greaterThan(userJoin.get("createdAt"), from))
                }
                if (createdTo != null) {
                    val to = createdTo.toLocalDate().atTime(LocalTime.MAX).plusSeconds(1)
                    predicates.add(criteriaBuilder.lessThan(userJoin.get("createdAt"), to))
                }
                criteriaBuilder.and(*predicates.toTypedArray())
            }
        }

        fun containsName(searchArray: Array<String>?): Specification<SupplierEntity>? {
            return if (!searchArray.isNullOrEmpty()) {
                Specification<SupplierEntity> { root, _, criteriaBuilder ->
                val predicates = mutableListOf<Predicate>()

                for (search in searchArray) {
                    if(StringUtils.hasText(search)) {
                        val predicateName: Predicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")), "%${search.lowercase()}%")
                        val predicateSurname: Predicate = criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("surName")), "%${search.lowercase()}%")
                        predicates.add(criteriaBuilder.or(predicateName, predicateSurname))
                    }
                }

                criteriaBuilder.and(*predicates.toTypedArray())}

            } else null
        }

        fun isGreaterThanCreatedAt(createdFrom: LocalDateTime?): Specification<SupplierEntity>? {
            return if (createdFrom != null) {
                val from = createdFrom.toLocalDate().atStartOfDay().minusSeconds(1)
                Specification<SupplierEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.greaterThan(root.get("createdAt"), from)
                }
            } else null
        }

        fun isLessThanCreatedAt(createdTo: LocalDateTime?): Specification<SupplierEntity>? {
            return if (createdTo != null) {
                val to = createdTo.toLocalDate().atTime(LocalTime.MAX).plusSeconds(1)
                Specification<SupplierEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.lessThan(root.get("createdAt"), to)
                }
            } else null
        }

        fun hasEnableStatus(enableStatus: AccountEnableStatus?): Specification<SupplierEntity>? {
            return if(enableStatus != null) {
                Specification<SupplierEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.equal(root.get<AccountEnableStatus>("enableStatus"), enableStatus)
                }
            } else null
        }

        fun isInActivityStatus(activityStatuses: Set<AccountActivityStatus>?): Specification<SupplierEntity>? {
            return if (activityStatuses != null && activityStatuses.isNotEmpty()) {
                Specification<SupplierEntity> { root, _, _ ->
                    root.get<AccountActivityStatus>("activityStatus").`in`(activityStatuses)
                }
            } else null
        }

        fun isInStatus(statuses: Set<SupplierStatus>?): Specification<SupplierEntity>? {
            return if (statuses != null && statuses.isNotEmpty()) {
                Specification<SupplierEntity> { root, _, _ ->
                    root.get<SupplierStatus>("status").`in`(statuses)
                }
            } else null
        }

//        fun professionJoinFilter(professionIds: Set<String>?): Specification<SupplierEntity>? {
//            return if (professionIds != null && professionIds.isNotEmpty()) {
//                Specification<SupplierEntity> { root, _, _ ->
//                    val professionJoin = root.join<SupplierEntity, ProfessionEntity>("profession")
//                    professionJoin.get<String>("id").`in`(professionIds)
//                }
//            } else null
//        }


        fun ratingIsGreaterOrEqualTo(ratingLowerBoundary: Float?): Specification<SupplierEntity>? {
            return if (ratingLowerBoundary != null) {
                Specification<SupplierEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("rating"), ratingLowerBoundary)
                }
            }
            else null
        }

        fun experienceIsLessThan(experienceUpperBoundary: Int?): Specification<SupplierEntity>? {
            return if ( experienceUpperBoundary != null) {
                val today = LocalDateTime.now().toLocalDate()
                println("LESS THAN ${today.minusYears(experienceUpperBoundary.toLong())}")
                Specification<SupplierEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.lessThan(root.get("experience"), today.minusYears(experienceUpperBoundary.toLong()))
                }
            }
            else null
        }

        fun experienceIsGreaterThan(experienceLowerBoundary: Int?): Specification<SupplierEntity>? {
            return if (experienceLowerBoundary != null) {
                val today = LocalDateTime.now().toLocalDate()
                println("GREATER THAN ${today.minusYears(experienceLowerBoundary.toLong())}")
                Specification<SupplierEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.greaterThan(root.get("experience"), today.minusYears(experienceLowerBoundary.toLong()))
                }
            }
            else null
        }

        fun ageIsLessThan(ageUpperBoundary: Long?): Specification<SupplierEntity>? {
            val today = LocalDateTime.now().toLocalDate()
            return if ( ageUpperBoundary != null) {
                Specification<SupplierEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.greaterThan(root.get("birthDate"), today.minusYears(ageUpperBoundary))
                }
            }
            else null
        }

        fun ageIsGreaterThanOrEqualTo(ageLowerBoundary: Long?): Specification<SupplierEntity>? {
            return if (ageLowerBoundary != null) {
                val today = LocalDateTime.now().toLocalDate()
                Specification<SupplierEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("birthDate"), today.minusYears(ageLowerBoundary))
                }
            }
            else null
        }

        fun isGender(gender: Gender?): Specification<SupplierEntity>? {
            return if (gender != null) {
                Specification<SupplierEntity> { root, _, _ ->
                    root.get<Gender>("userGender").`in`(gender)
                }
            } else null
        }

        fun priceIsLessThan(priceUpperBoundary: Int?): Specification<SupplierEntity>? {
            return if (priceUpperBoundary != null) {
                Specification<SupplierEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.lessThan(root.get("videoPerHour"), priceUpperBoundary)
                }
            }
            else null
        }
        fun priceIsGreaterThan(priceLowerBoundary: Int?): Specification<SupplierEntity>? {
            return if (priceLowerBoundary != null) {
                Specification<SupplierEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.greaterThan(root.get("videoPerHour"), priceLowerBoundary)
                }
            }
            else null
        }
        fun isInOsType(osTypes: Set<OsType>?): Specification<SupplierEntity>? {
            return if (osTypes != null && osTypes.isNotEmpty()) {
                Specification<SupplierEntity> { root, _, _ ->
                    root.get<AccountActivityStatus>("osType").`in`(osTypes)
                }
            } else null
        }
    }
}