package dev.december.jeterbackend.shared.features.feedbacks.domain.models

import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import org.springframework.data.domain.Sort

enum class FeedbackSortField(private val sortField: String) {
    CREATED_AT("createdAt"),
    CLIENT_NAME("client.fullName"),
    SUPPLIER_NAME("supplier.fullName");

    fun getSortFields(sortDirection: SortDirection): Sort {
        return if (sortDirection == SortDirection.DESC) Sort.by(this.sortField).descending()
        else Sort.by(this.sortField).ascending()
    }
}