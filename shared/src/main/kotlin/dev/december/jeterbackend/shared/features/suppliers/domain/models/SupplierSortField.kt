package dev.december.jeterbackend.shared.features.suppliers.domain.models

import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import org.springframework.data.domain.Sort

enum class SupplierSortField(val sortField: String) {
    CREATED_AT("createdAt"),
    NAME("fullName"),
    SESSION("sessionCount"),
    EARNINGS("totalEarnings");

    fun getSortFields(sortDirection: SortDirection): Sort {
        return if (sortDirection == SortDirection.DESC) Sort.by(this.sortField).descending()
        else Sort.by(this.sortField).ascending()
    }
}