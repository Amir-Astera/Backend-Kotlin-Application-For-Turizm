package dev.december.jeterbackend.shared.core.utils

import com.mapk.kmapper.KMapper

inline fun <T : Any, reified R : Any> T.convert(
    extraFields: Map<String, Any?> = emptyMap()
): R {
    return KMapper(R::class).map(extraFields, this)
}