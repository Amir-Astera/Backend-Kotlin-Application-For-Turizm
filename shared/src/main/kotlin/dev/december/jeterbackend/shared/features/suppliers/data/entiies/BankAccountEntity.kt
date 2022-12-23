package dev.december.jeterbackend.shared.features.suppliers.data.entiies

import java.util.*
import javax.persistence.*

@Entity(name = "bank_account")
data class BankAccountEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @OneToOne(mappedBy = "bankAccount")
    private val supplier: SupplierEntity? = null,
    @Column(name = "card_number")
    val cardNumber: String?,
    @Column(name = "company_name")
    val companyName: String?,
    @Column(name = "company_address")
    val companyAddress: String?,
    @Column(name = "bin")
    val bin: String?,
    @Column(name = "kbe")
    val kbe: String?,
    @Column(name = "bik")
    val bik: String?,
    @Column(name = "iik")
    val iik: String?,
)