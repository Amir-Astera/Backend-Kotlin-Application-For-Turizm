package dev.december.jeterbackend.shared.features.suppliers.domain.models.enums

enum class SupplierExperienceRange {
    LESS_THAN_5,
    FROM_5_TO_10,
    MORE_THAN_10;

    fun getExperienceUpperBoundary(experience: SupplierExperienceRange): Int? {
        return when (experience) {
            LESS_THAN_5 -> null
            FROM_5_TO_10 -> 4
            MORE_THAN_10 -> 10
        }
    }

    fun getExperienceLowerBoundary(experience: SupplierExperienceRange): Int? {
        return when (experience) {
            LESS_THAN_5 -> 5
            FROM_5_TO_10 -> 11
            MORE_THAN_10 -> null
        }
    }
}