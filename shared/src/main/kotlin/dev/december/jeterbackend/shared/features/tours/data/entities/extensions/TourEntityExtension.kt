package dev.december.jeterbackend.shared.features.tours.data.entities.extensions

import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.clients.data.entities.extensions.client
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import dev.december.jeterbackend.shared.features.files.domain.models.File
import dev.december.jeterbackend.shared.features.payments.domain.models.Payment
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.extensions.supplier
import dev.december.jeterbackend.shared.features.tours.data.entities.TourEntity
import dev.december.jeterbackend.shared.features.tours.domain.models.Tour


fun TourEntity.tour(): Tour {
    return convert(
        mapOf (
            "client" to this.client.client(),
            "supplier" to this.supplier.supplier(),
            "receipt" to this.receipt?.convert<FileEntity, File>()
        )
    )
}

fun TourEntity.payment(): Payment {
    return convert(
        mapOf(
            "client" to this.client.client(),
            "supplier" to this.supplier.supplier()
        )
    )
}