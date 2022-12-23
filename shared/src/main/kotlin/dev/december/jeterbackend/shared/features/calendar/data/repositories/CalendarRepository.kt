package dev.december.jeterbackend.shared.features.calendar.data.repositories

import dev.december.jeterbackend.shared.features.calendar.data.entities.CalendarEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface CalendarRepository : CrudRepository<CalendarEntity, String> {
    fun findBySupplierIdAndFirstDayOfMonth(
        supplierId: String,
        firstDayOfMonth: LocalDate
    ): CalendarEntity?
}