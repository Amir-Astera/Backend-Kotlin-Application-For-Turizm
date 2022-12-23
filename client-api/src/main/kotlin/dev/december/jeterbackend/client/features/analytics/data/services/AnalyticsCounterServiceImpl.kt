package dev.december.jeterbackend.client.features.analytics.data.services

import dev.december.jeterbackend.client.features.authorization.domain.errors.RefreshTokenFailure
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.analytics.data.entities.AnalyticsCounterEntity
import dev.december.jeterbackend.shared.features.analytics.data.repositories.AnalyticsCounterRepository
import dev.december.jeterbackend.client.features.analytics.domain.services.AnalyticsCounterService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AnalyticsCounterServiceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val acr: AnalyticsCounterRepository
): AnalyticsCounterService {
    override suspend fun countLogin(): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val analyticsCounter = acr.findByCreatedAt(LocalDate.now())
                if (analyticsCounter == null) {
                    acr.save( AnalyticsCounterEntity(
                        supplierLogins = 1,
                        totalLogins = 1
                    )
                    )
                } else acr.save(analyticsCounter.copy(
                    supplierLogins = analyticsCounter.supplierLogins + 1,
                    totalLogins = analyticsCounter.totalLogins + 1
                ))
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(RefreshTokenFailure())//SupplierCountLoginFailure()
        }
    }

    override suspend fun countCreate(): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val analyticsCounter = acr.findByCreatedAt(LocalDate.now())
                if (analyticsCounter == null) {
                    acr.save( AnalyticsCounterEntity(
                        suppliersCreated = 1,
                        totalCreated = 1
                    ))
                } else acr.save(analyticsCounter.copy(
                    suppliersCreated = analyticsCounter.suppliersCreated + 1,
                    totalCreated = analyticsCounter.totalCreated + 1
                ))
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            Data.Error(RefreshTokenFailure())//SupplierCountCreateFailure()
        }
    }
}