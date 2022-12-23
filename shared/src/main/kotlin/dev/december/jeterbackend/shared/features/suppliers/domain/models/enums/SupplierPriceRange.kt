package dev.december.jeterbackend.shared.features.suppliers.domain.models.enums

enum class SupplierPriceRange {
    MIN,
    MEDIUM,
    MAX;

    fun getPriceLowerBoundary(price: SupplierPriceRange): Int? {
        return when (price) {
            MIN -> null
            MEDIUM -> 14999
            MAX -> 29999
        }
    }

    fun getPriceUpperBoundary(price: SupplierPriceRange): Int? {
        return when (price) {
            MIN -> 15000
            MEDIUM -> 30000
            MAX -> null
        }
    }
}