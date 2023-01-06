package dev.december.jeterbackend.shared.core.domain.model

enum class OsType {
    IOS,
    ANDROID,
    UNKNOWN;

    fun get(osType: String?): OsType {
        return when (osType) {
            "\"Android\"" -> ANDROID
            "\"iOS\"" -> IOS
            else -> UNKNOWN
        }
    }
}