package dev.december.jeterbackend.shared.features.promocodes.domain.services

import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.promocodes.domain.models.*
import org.springframework.data.domain.Page
import java.time.LocalDateTime

interface PromocodeService {
    suspend fun create(
        code: String,
        description: String?,
        discountType: PromocodeDiscountType,
        discountAmount: Int?,
        discountPercentage: Double?,
        activationLimit: Int,
        validityFrom: LocalDateTime,
        validityTo: LocalDateTime,
        adminId: String?,
        userId: String?,
    ): Data<String>

    suspend fun update(
        promoId: String,
        adminId: String?,
        userId: String?,
        code: String?,
        description: String?,
        discountType: PromocodeDiscountType?,
        discountAmount: Int?,
        discountPercentage: Double?,
        activationLimit: Int?,
        validityFrom: LocalDateTime?,
        validityTo: LocalDateTime?,
        updatedAt: LocalDateTime,
    ): Data<Unit>

    suspend fun disableList(
        ids: List<String>,
    ): Data<Unit>

    suspend fun get(
        id: String,
        userId: String?,
    ): Data<Promocode>

    suspend fun getList(
        userId: String?,
        supplierId: String?,
        statuses: Set<PromocodeStatus>?,
        types: Set<PromocodeDiscountType>?,
        searchField: String?,
        sortField: PromocodeSortField,
        sortDirection: SortDirection,
        page: Int,
        size: Int,
        createdFrom: LocalDateTime?,
        createdTo: LocalDateTime?
    ): Data<Page<Promocode>>

    suspend fun updateStatus(
        id: String,
        userId: String?,
        status: PromocodeStatus,
    ): Data<Unit>

    suspend fun generateTitle(): Data<String>

    suspend fun getRandomString(length: Int): String

    suspend fun delete(id: String, userId: String?): Data<Unit>

    suspend fun expirationJob(): Data<Unit>

    suspend fun validatePromocode(code: String, supplierId: String, userId: String): Data<Promocode>

    suspend fun getFinalPrice(promocode: Promocode, price: Double): Data<PromocodeDiscountAmount>

    suspend fun applyPromocode(promocodeId: String, userId: String): Data<Unit>
}