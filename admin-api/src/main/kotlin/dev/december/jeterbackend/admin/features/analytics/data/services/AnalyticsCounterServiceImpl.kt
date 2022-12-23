package dev.december.jeterbackend.admin.features.analytics.data.services

import dev.december.jeterbackend.admin.features.analytics.domain.errors.AnalyticsCounterNotFoundFailure
import dev.december.jeterbackend.admin.features.analytics.domain.errors.AnalyticsGetListFailure
import dev.december.jeterbackend.admin.features.analytics.domain.errors.AnalyticsTodayFailure
import dev.december.jeterbackend.admin.features.analytics.domain.services.AnalyticsCounterService
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.analytics.data.entities.AnalyticsCounterEntity
import dev.december.jeterbackend.shared.features.analytics.data.repositories.AnalyticsCounterRepository
import dev.december.jeterbackend.shared.features.analytics.data.repositories.specifications.AnalyticsCounterSpecification
import dev.december.jeterbackend.shared.features.analytics.domain.models.AnalyticsCounter
import dev.december.jeterbackend.shared.features.analytics.domain.models.AnalyticsCounterSortDirection
import dev.december.jeterbackend.shared.features.analytics.domain.models.AnalyticsCounterSortField
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class AnalyticsCounterServiceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val acr: AnalyticsCounterRepository
): AnalyticsCounterService {
    override suspend fun getToday(): Data<AnalyticsCounter> {
        return try {
            withContext(dispatcher) {
                val analyticsCounterEntity = acr.findByCreatedAt(LocalDate.now())
                    ?: return@withContext Data.Success(acr.save(AnalyticsCounterEntity()).convert())
                val analyticsCounter = analyticsCounterEntity.convert<AnalyticsCounterEntity, AnalyticsCounter>()
                Data.Success(analyticsCounter)
            }
        } catch (e: Exception) {
            Data.Error(AnalyticsTodayFailure())
        }
    }

    override suspend fun getAll(
        sortField: AnalyticsCounterSortField,
        sortDirection: AnalyticsCounterSortDirection,
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?
    ): Data<Page<AnalyticsCounter>> {
        return try {
            withContext(dispatcher) {
                val sortParams: Sort = sortField.getSortField(sortDirection)

                val pageable = PageRequest.of(page, size, sortParams)

                val specifications: Specification<AnalyticsCounterEntity> =
                    Specification.where(
                        AnalyticsCounterSpecification.isGreaterThanCreatedAt(createdFrom)
                            ?.and(AnalyticsCounterSpecification.isLessThanCreatedAt(createdTo))
                    )

                val entities: Page<AnalyticsCounterEntity> = acr.findAll(specifications, pageable)
                val analytics: Page<AnalyticsCounter> = entities.map { analyticsEntity ->
                    analyticsEntity.convert() }
                Data.Success(analytics)
            }
        } catch (e: Exception) {
            Data.Error(AnalyticsGetListFailure())
        }
    }

    override suspend fun getByDate(date: LocalDateTime): Data<AnalyticsCounter> {
        return try {
            withContext(dispatcher) {
                val analyticsCounterEntity = acr.findByCreatedAt(date.toLocalDate())
                    ?: return@withContext Data.Error(AnalyticsCounterNotFoundFailure())
                val analyticsCounter = analyticsCounterEntity.convert<AnalyticsCounterEntity, AnalyticsCounter>()
                Data.Success(analyticsCounter)
            }
        } catch (e: Exception) {
            Data.Error(AnalyticsGetListFailure())
        }
    }
}