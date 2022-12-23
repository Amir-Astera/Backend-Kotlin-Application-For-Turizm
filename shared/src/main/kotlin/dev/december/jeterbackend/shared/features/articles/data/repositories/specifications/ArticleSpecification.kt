package dev.december.jeterbackend.shared.features.articles.data.repositories.specifications

import dev.december.jeterbackend.shared.features.articles.data.entities.ArticleEntity
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.LocalTime

@Component
class ArticleSpecification{
    companion object{
        fun isGreaterThanCreatedAt(updatedFrom: LocalDateTime?): Specification<ArticleEntity> ?{
            return if (updatedFrom != null){
                val from = updatedFrom.toLocalDate().atStartOfDay().minusSeconds(1)
                Specification<ArticleEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.greaterThan(root.get("createdAt"), from)
                }
            } else null
        }

        fun isLessThanCreatedAt(updatedTo: LocalDateTime?): Specification<ArticleEntity>? {
            return if (updatedTo != null){
                val to = updatedTo.toLocalDate().atTime(LocalTime.MAX).plusSeconds(1)
                Specification<ArticleEntity> { root, _, criteriaBuilder ->
                    criteriaBuilder.lessThan(root.get("createdAt"), to)
                }
            } else null
        }

//        fun hasProfession(professionEntity: ProfessionEntity): Specification<ArticleEntity>? {
//            return Specification<ArticleEntity> { root, _, criteriaBuilder ->
//                criteriaBuilder.equal(root.get<Profession>("profession"), professionEntity)
//            }
//        }

    }
}