package dev.december.jeterbackend.shared.features.suppliers.domain.models.enums

enum class SupplierAgeRange {
    LESS_THAN_30,
    FROM_30_TO_45,
    MORE_THAN_45;

    fun getAgeLowerBoundaryIncl(age: SupplierAgeRange): Long? {
        return when (age) {
            LESS_THAN_30 -> null
            FROM_30_TO_45 -> 30
            MORE_THAN_45 -> 46
        }
    }

    fun getAgeUpperBoundaryNonIncl(age: SupplierAgeRange): Long? {
        return when (age) {
            LESS_THAN_30 -> 30
            FROM_30_TO_45 -> 46
            MORE_THAN_45 -> null
        }
    }
}