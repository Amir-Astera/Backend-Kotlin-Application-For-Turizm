package dev.december.jeterbackend.shared.features.analytics.data.repositories


import dev.december.jeterbackend.shared.features.analytics.data.entities.AnalyticsCounterEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface AnalyticsCounterRepository: PagingAndSortingRepository<AnalyticsCounterEntity, String>,
    JpaSpecificationExecutor<AnalyticsCounterEntity>, JpaRepository<AnalyticsCounterEntity, String> {
    fun findByCreatedAt(date: LocalDate): AnalyticsCounterEntity?
}