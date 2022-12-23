package dev.december.jeterbackend.stream.signaler.data.model

import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier

data class ResponseMessageModel(
    val client: Client? = null,
    val supplier: Supplier? = null,
    val message: String,
)
