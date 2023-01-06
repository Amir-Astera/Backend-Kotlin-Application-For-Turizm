package dev.december.jeterbackend.admin.features.admin.domain.services

import dev.december.jeterbackend.admin.features.admin.presentation.dto.UpdateAdminByUserIdData
import dev.december.jeterbackend.admin.features.admin.presentation.dto.UpdateAdminData
import dev.december.jeterbackend.shared.core.domain.model.AccountActivityStatus
import dev.december.jeterbackend.shared.core.domain.model.AccountEnableStatus
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.domain.model.Gender
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.admin.data.entities.AdminEntity
import dev.december.jeterbackend.shared.features.admin.domain.models.Admin
import dev.december.jeterbackend.shared.features.admin.domain.models.AdminAuthorityCode
import dev.december.jeterbackend.shared.features.admin.domain.models.AdminSortField
import org.springframework.data.domain.Page
import java.time.LocalDate
import java.time.LocalDateTime

interface AdminService {

    suspend fun getList(
        sortField: AdminSortField,
        sortDirection: SortDirection,
        page: Int,
        size: Int,
        searchField: String?,
        authorityCodes: Set<AdminAuthorityCode>,
        activityStatuses: Set<AccountActivityStatus>?,
        enableStatus: AccountEnableStatus?,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?,
    ): Data<Page<Admin>>

    suspend fun create(
        email: String,
        phone: String,
        password: String,
        fullName: String,
        birthDate: LocalDate?,
        gender: Gender?,
        avatarId: String?,
        successOnExists: ((AdminEntity) -> Boolean)? = null,
    ): Data<String>

    suspend fun addUserClaims(userId: String, claims: Map<String, Any>): Data<Unit>

    suspend fun updateByUserId(
        adminId: String,
        updateAdminByUserIdData: UpdateAdminByUserIdData?
    ): Data<String>

    suspend fun update(
        id: String,
        updateAdminData: UpdateAdminData?
    ): Data<String>

    suspend fun getByUserId(userId: String): Data<Admin>

    suspend fun delete(
        adminId: String
    ): Data<Unit>

    suspend fun deleteList(
        ids: Set<String>
    ): Data<Unit>
}