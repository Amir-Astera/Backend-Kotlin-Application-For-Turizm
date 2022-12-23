package dev.december.jeterbackend.shared.features.suppliers.domain.models.enums

enum class SupplierRatingRange {
    FROM_3,
    FROM_4;

    fun getRatingLowerBoundary(rating: SupplierRatingRange): Float {
        return when (rating) {
            FROM_3 -> 3f
            FROM_4 -> 4f
        }
    }
}