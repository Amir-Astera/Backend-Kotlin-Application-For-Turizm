package dev.december.jeterbackend.shared.features.suppliers.domain.models


data class SupplierBankAccount(
    val cardNumber: String?,
    val companyName: String?,
    val companyAddress: String?,
    val bin: String?,
    val kbe: String?,
    val bik: String?,
    val iik: String?,
)