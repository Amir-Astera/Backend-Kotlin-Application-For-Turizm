package dev.december.jeterbackend.scheduler.features.supplier.data.services

import dev.december.jeterbackend.scheduler.core.config.properties.TaskProperties
import dev.december.jeterbackend.scheduler.core.config.security.firebase.FirebaseConfig
import dev.december.jeterbackend.scheduler.features.files.domain.services.FileService
import dev.december.jeterbackend.scheduler.features.supplier.domain.errors.DeleteSupplierFailure
import dev.december.jeterbackend.scheduler.features.supplier.domain.errors.UpdateSupplierActivityStatusFailure
import dev.december.jeterbackend.scheduler.features.supplier.domain.services.SupplierService
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.calendar.data.repositories.CalendarRepository
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.features.suppliers.data.repositories.SupplierRepository
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class SupplierServiceImpl(
    private val supplierRepository: SupplierRepository,
    private val dispatcher: CoroutineDispatcher,
    private val calendarRepository: CalendarRepository,
    private val fileService: FileService,
    private val firebaseConfig: FirebaseConfig,
    private val taskProperties: TaskProperties,
    private val logger: Logger
) : SupplierService {

    override suspend fun resetActivityStatus(): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val suppliers = supplierRepository.findAllByActivityStatusAndEnableStatusAndStatus(
                    AccountActivityStatus.ACTIVE,
                    AccountEnableStatus.ENABLED,
                    SupplierStatus.APPROVED
                )
                suppliers.forEach { supplier ->
                    val isValid = checkSupplierFieldsForActivityStatus(supplier, LocalDate.now())
                    if (!isValid) {
                        supplierRepository.save(
                            supplier.copy(
                                activityStatus = AccountActivityStatus.INACTIVE
                            )
                        )
                    }
                }
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            logger.error(e.message)
            Data.Error(UpdateSupplierActivityStatusFailure())
        }
    }

    override suspend fun delete(): Data<Unit> {
        return try {
            withContext(dispatcher) {
                val minusDays = taskProperties.supplierTask.minusDays
                val deleteDay = LocalDateTime.now().minusDays(minusDays.toLong())
                val from = deleteDay.with(LocalTime.MIN)
                val to = deleteDay.with(LocalTime.MAX)
                val suppliers = supplierRepository.findAllByEnableStatusAndDeletedAtBetween(AccountEnableStatus.DISABLED, from, to)
                suppliers.forEach { supplier ->
                    firebaseConfig.firebaseAuth(PlatformRole.SUPPLIER).deleteUser(supplier.id)
                    deleteFiles(supplier)
                }
                Data.Success(Unit)
            }
        } catch (e: Exception) {
            logger.error(e.message)
            Data.Error(DeleteSupplierFailure())
        }
    }

    private suspend fun deleteFiles(supplier: SupplierEntity) {
        supplier.passportFiles.forEach { file ->
            fileService.deleteFile(file.id, file.directory, file.format)
        }
        if (supplier.avatar != null) {
            fileService.deleteFile(
                supplier.avatar!!.id,
                supplier.avatar!!.directory,
                supplier.avatar!!.format
            )
        }
        supplier.files.forEach { file ->
            fileService.deleteFile(file.id, file.directory, file.format)
        }
        if (supplier.file != null) {
            fileService.deleteFile(
                supplier.file!!.id,
                supplier.file!!.directory,
                supplier.file!!.format
            )
        }
    }

    private fun checkSupplierFieldsForActivityStatus(
        supplier: SupplierEntity,
        firstDayOfMonth: LocalDate,
    ): Boolean {
        if (supplier.name == "" || supplier.surname == "" || supplier.patronymic == "") {
            return false
        }
        if (supplier.education == null || supplier.education?.size == 0) {
            return false
        }
        // TODO: add checker for bank account
//        if (supplier.bankAccount == null) {
//            return false
//        }
        if (supplier.videoPerHour == null) {
            return false
        }
        if (supplier.audioPerHour == null) {
            return false
        }
        val calendar = calendarRepository.findBySupplierIdAndFirstDayOfMonth(supplier.id, firstDayOfMonth)
            ?: return false
        return true
    }
}