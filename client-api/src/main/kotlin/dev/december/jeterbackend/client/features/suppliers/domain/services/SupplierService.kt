package dev.december.jeterbackend.client.features.suppliers.domain.services

import dev.december.jeterbackend.client.features.suppliers.presentation.dto.FreeTimeDto
import dev.december.jeterbackend.shared.core.domain.model.Gender
import dev.december.jeterbackend.shared.core.results.Data
import dev.december.jeterbackend.shared.features.calendar.domain.models.Calendar
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierProfileInformation
import dev.december.jeterbackend.shared.features.suppliers.domain.models.enums.SupplierAgeRange
import dev.december.jeterbackend.shared.features.suppliers.domain.models.enums.SupplierExperienceRange
import dev.december.jeterbackend.shared.features.suppliers.domain.models.enums.SupplierPriceRange
import dev.december.jeterbackend.shared.features.suppliers.domain.models.enums.SupplierRatingRange
import org.springframework.data.domain.Page
import java.time.LocalDate

interface SupplierService {
    suspend fun getList(
        userId: String,
        page: Int,
        size: Int,
        searchField: String?,
        ratingFilter: SupplierRatingRange?,
        experienceFilter: SupplierExperienceRange?,
        ageFilter: SupplierAgeRange?,
        genderFilter: Gender?,
        priceFilter: SupplierPriceRange?,
    ): Data<Page<Supplier>>

    suspend fun getSupplier(clientId: String, supplierId: String): Data<SupplierProfileInformation>

    suspend fun getCalendar(supplierId: String, firstDayOfMonth: LocalDate,): Data<Calendar>


    suspend fun getSupplierFreeTime(
        supplierId: String,
        date: LocalDate
    ): Data<List<FreeTimeDto>>
}