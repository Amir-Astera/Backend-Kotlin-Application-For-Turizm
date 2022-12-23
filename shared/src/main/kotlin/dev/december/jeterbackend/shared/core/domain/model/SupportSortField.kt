package dev.december.jeterbackend.shared.core.domain.model

import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import org.springframework.data.domain.Sort

enum class SupportSortField(private val sortField: String) {
    NAME("fullName"),
    CREATED_AT("createdAt"),
    PHONE("phone");


    fun getSortField(sortDirection: SortDirection): Sort {
        return if (sortDirection == SortDirection.DESC) Sort.by(this.sortField).descending()
        else Sort.by(this.sortField).ascending()
    }

}
