package dev.december.jeterbackend.shared.features.promocodes.domain.models

import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import org.springframework.data.domain.Sort

enum class PromocodeSortField(val sortField: String, private val withPrefix: Boolean = false) {
    CREATED_AT("createdAt"),
    CODE("code"),
    TYPE("discountType");

    fun getSortFields(sortDirection: SortDirection): Sort {
        return if (sortDirection == SortDirection.DESC) Sort.by(this.sortField).descending()
        else Sort.by(this.sortField).ascending()
    }
}