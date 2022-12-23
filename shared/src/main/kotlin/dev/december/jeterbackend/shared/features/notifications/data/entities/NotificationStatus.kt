package dev.december.jeterbackend.shared.features.notifications.data.entities

enum class NotificationStatus {
    NOT_SENT,
    HAVE_BEEN_SENT_BEFORE_TEM_MINUTES,
    HAVE_BEEN_SENT_BEFORE_APPOINTMENT,
}