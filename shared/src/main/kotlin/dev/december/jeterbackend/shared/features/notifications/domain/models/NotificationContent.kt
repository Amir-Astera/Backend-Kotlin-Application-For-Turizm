package dev.december.jeterbackend.shared.features.notifications.domain.models

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class NotificationContent(
    val title: String,
    val body: String,
) {

    fun formatTitle(
        name: String? = null,
        date: LocalDateTime? = null
    ): String = formatMessage(
        message = this.title,
        name = name,
        date = date
    )

    fun formatBody(
        name: String? = null,
        date: LocalDateTime? = null
    ): String = formatMessage(
        message = this.body,
        name = name,
        date = date,
    )

    private fun formatMessage(
        message: String,
        name: String? = null,
        date: LocalDateTime? = null,
    ): String {
        var result = message
        if (name != null) {
            result = result.replace("{name}", name)
        }
        if (date != null) {
            val formattedDate = date.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            )
            result = result.replace("{date}", formattedDate)
        }
        return result
    }
}
