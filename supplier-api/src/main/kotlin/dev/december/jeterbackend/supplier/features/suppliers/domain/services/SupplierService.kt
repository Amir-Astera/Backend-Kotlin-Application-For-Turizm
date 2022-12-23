package dev.december.jeterbackend.supplier.features.suppliers.domain.services

import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.calendar.domain.models.Calendar
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.features.suppliers.domain.models.*
import dev.december.jeterbackend.shared.features.tours.domain.models.SupplierPrice
import java.time.LocalDate

interface SupplierService {
    suspend fun get(userId: String): Data<String>//<Supplier>
    suspend fun getById(supplierId: String): Data<String>//<Supplier>
    suspend fun updateSchedule( userId: String, schedule: SupplierSchedule): Data<String>

    suspend fun addEducation(userId: String, education: SupplierEducation): Data<String>
    suspend fun deleteEducation(supplier: Supplier, educationId: String): Data<String>
    suspend fun updateEducation(supplier: Supplier, educationId: String, data: SupplierEducationUpdate): Data<String>

    suspend fun addCertificate(supplier: Supplier, certificate: SupplierCertificate): Data<String>
    suspend fun deleteCertificate(supplier: Supplier, certificateId: String): Data<String>
    suspend fun updateCertificate(supplier: Supplier, certificateId: String, data: SupplierCertificateUpdate): Data<String>

    suspend fun updateGeneralInfo(userId: String, generalInfo: SupplierGeneralInfoUpdate): Data<String>
    suspend fun updateBankAccount(userId: String, bankAccount: SupplierBankAccount): Data<String>

    suspend fun updatePrice(userId: String, price: SupplierPrice): Data<String>
    suspend fun restore(id: String, signInProvider: String?): Data<Unit>
    suspend fun deleteByUserId(id: String, signInProvider: String?): Data<Unit>
    suspend fun disableRoleSupplier(userId: String)
    suspend fun createSupplier(
        email: String,
        phone: String,
        password: String,
        generalInfo: SupplierGeneralInfo,
        education: List<SupplierEducation>,
        certificate: List<SupplierCertificate>,
        socialMedia: SupplierSocialMedia?,
        bankAccount: SupplierBankAccount?,
        avatar: File?,
        schedule: SupplierSchedule?,
        price: SupplierPrice?,
        files: List<File>?,
        registrationToken: String?,
        successOnExists: ((SupplierEntity) -> Boolean)? = null,
    ): Data<String>

    suspend fun getCalendar(userId: String, firstDayOfMonth: LocalDate,): Data<String>//<Calendar>
    suspend fun updateCalendar(userId: String, firstDayOfMonth: LocalDate, workingDays: Set<LocalDate>,): Data<Unit>

    suspend fun updateSocialMedia( userId: String, socialMedia: SupplierSocialMedia): Data<String>
    suspend fun toggleNotification(userId: String, toggleStatus: Boolean): Data<Unit>
    suspend fun getByPhone(phone: String): Data<Boolean>
    suspend fun updateRegistrationToken(userId: String, registrationToken: String): Data<Unit>
    suspend fun updateVideoPrice(userId: String, price: Int?): Data<Unit>
    suspend fun updateAudioPrice(userId: String, price: Int?): Data<Unit>
    suspend fun updateDescription(userId: String, description: String): Data<Unit>
    suspend fun updateActivityStatus(userId: String, activityStatus: AccountActivityStatus, firstDayOfMonth: LocalDate): Data<Unit>

    suspend fun resetActivityStatus(): Data<Unit>

    suspend fun updateExperience(userId: String, experience: LocalDate?): Data<Unit>

    suspend fun addUserClaims(userId: String, claims: Map<String, Any>): Data<String>
}