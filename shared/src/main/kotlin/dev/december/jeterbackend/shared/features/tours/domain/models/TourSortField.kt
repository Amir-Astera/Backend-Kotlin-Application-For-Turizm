package dev.december.jeterbackend.shared.features.tours.domain.models

import dev.december.jeterbackend.shared.core.domain.model.PlatformRole
import dev.december.jeterbackend.shared.core.domain.model.SortDirection
import org.springframework.data.domain.Sort

enum class TourSortField(val sortField: String, private val withPrefix: Boolean = false) {
    CREATED_AT("createdAt"),
    NAME("fullName", true),
    RESERVATION_DATE("reservationDate");

    fun getSortFields(sortDirection: SortDirection, authority: PlatformRole = PlatformRole.SUPPLIER): Sort {
        val prefix = if (this.withPrefix) {
            if (authority == PlatformRole.CLIENT) "supplier."
            else "client."
        } else ""

        return if (sortDirection == SortDirection.DESC) Sort.by(prefix + this.sortField).descending()
            else Sort.by(prefix + this.sortField).ascending()
    }

}