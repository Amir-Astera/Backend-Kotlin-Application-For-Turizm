package dev.december.jeterbackend.admin.core.config

import dev.december.jeterbackend.shared.features.files.domain.models.FileDirectory
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.util.*

@Component
class FileDirectoryConverter : Converter<String, FileDirectory> {
    override fun convert(value: String): FileDirectory {
        return FileDirectory.valueOf(value.uppercase(Locale.getDefault()))
    }
}