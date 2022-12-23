package dev.december.jeterbackend.shared.features.appointments.data.entities.extensions

import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.appointments.data.entities.AppointmentEntity
import dev.december.jeterbackend.shared.features.appointments.domain.models.Appointment
import dev.december.jeterbackend.shared.features.clients.data.entities.extensions.client
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.payments.domain.models.Payment
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.extensions.supplier

fun AppointmentEntity.appointment(): Appointment {
    return convert(
        mapOf (
            "client" to this.client.client(),
            "supplier" to this.supplier.supplier(),
            "receipt" to this.receipt?.convert<FileEntity, File>()
        )
    )
}

fun AppointmentEntity.payment(): Payment {
    return convert(
        mapOf(
            "client" to this.client.client(),
            "supplier" to this.supplier.supplier()
        )
    )
}