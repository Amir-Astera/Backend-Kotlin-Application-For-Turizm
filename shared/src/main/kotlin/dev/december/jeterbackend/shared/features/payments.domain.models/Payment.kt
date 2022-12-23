package dev.december.jeterbackend.shared.features.payments.domain.models

import java.time.LocalDateTime
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

data class Payment(
    val id: String,
//    val client: Client,
//    val supplier: Supplier,
    val payment: Int,
    val reservationDate: LocalDateTime
)
