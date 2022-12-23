package dev.december.jeterbackend.shared.features.appointments.domain.models

import com.fasterxml.jackson.annotation.JsonProperty

enum class AppointmentStatus {
    SUPPLIER_SUBMITTED,
    CLIENT_SUBMITTED,
    CONFIRMED,
//    IN_PROGRESS,
    CANCELED,
    COMPLETED,
}