package dev.december.jeterbackend.shared.features.notifications.domain.models

import org.springframework.data.domain.Sort

enum class NotificationType(
    val data: String,
    val title: String,
    val body: String,
) {
    APPOINTMENT_CREATED(
        "APPOINTMENT_CREATED",
        "%s has requested an appointment with you!",
        "The appointment is requested to be at time %s. You can confirm the appointment in the app!"
    ),

    APPOINTMENT_CONFIRMED (
        "APPOINTMENT_CONFIRMED",
        "%s confirmed appointment!",
        "Your appointment is going to be at time %s."
    ),

    APPOINTMENT_CHANGED_TIME (
        "APPOINTMENT_CHANGED_TIME",
        "%s changed time!",
        "The appointment with %s is requested to be at time %s. You can confirm the appointment in the app!"
    ),

    APPOINTMENT_SOON (
        "APPOINTMENT_SOON",
        "Appointment soon!",
        "Your appointment with %s is starting in 10 minutes!"

    ),

    APPOINTMENT_STARTED (
        "APPOINTMENT_STARTED",
        "Appointment started!",
        "The appointment with %s has been started!"
    ),

    APPOINTMENT_CANCELED (
        "APPOINTMENT_CANCELED",
        "Appointment canceled!",
        "The appointment with %s at %s canceled!"
    );

}