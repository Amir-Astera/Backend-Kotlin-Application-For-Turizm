package dev.december.jeterbackend.stream.signaler.data.model

import dev.december.jeterbackend.shared.features.tours.domain.models.TourStatus
import dev.december.jeterbackend.stream.signaler.data.model.ClientModel
import dev.december.jeterbackend.stream.signaler.data.model.SupplierModel
import java.util.*
import javax.persistence.*

data class AppointmentModel(
    val status: TourStatus,
    val supplier: SupplierModel? = null,
    val client: ClientModel? = null,
)