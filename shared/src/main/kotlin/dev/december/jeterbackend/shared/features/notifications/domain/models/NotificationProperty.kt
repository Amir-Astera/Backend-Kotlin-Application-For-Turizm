package dev.december.jeterbackend.shared.features.notifications.domain.models

import dev.december.jeterbackend.shared.core.domain.model.Language

data class NotificationProperty(
    val type: String,
    val ru: NotificationContent,
    val kk: NotificationContent,
    val en: NotificationContent,
) {
    fun getContentByLanguage(language: Language): NotificationContent {
        return when (language) {
            Language.RU -> this.ru
            Language.KK -> this.kk
            else -> this.en
        }
    }
}