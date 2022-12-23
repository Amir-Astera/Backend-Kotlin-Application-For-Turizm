package dev.december.jeterbackend.shared.features.promocodes.data.entities.extensions

import dev.december.jeterbackend.shared.features.promocodes.data.entities.PromocodeEntity
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.promocodes.domain.models.Promocode
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.extensions.supplier

fun PromocodeEntity.promocode(): Promocode {
    val supplier = this.supplier?.supplier()
    val admin = this.admin?.admin()
    return this.convert(mapOf("supplier" to supplier, "admin" to admin))
}