package dev.december.jeterbackend.shared.features.suppliers.data.entiies.extensions

import dev.december.jeterbackend.shared.core.domain.model.UserAuthority
import dev.december.jeterbackend.shared.core.domain.model.UserIdentity
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.CertificateEntity
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.EducationEntity
import dev.december.jeterbackend.shared.features.suppliers.data.entiies.SupplierEntity
import dev.december.jeterbackend.shared.features.suppliers.domain.models.Supplier
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierCertificateResponse
import dev.december.jeterbackend.shared.features.suppliers.domain.models.SupplierEducationResponse
import dev.december.jeterbackend.shared.core.utils.convert
import dev.december.jeterbackend.shared.features.authorities.data.entities.authority
import dev.december.jeterbackend.shared.features.authorities.domain.models.AuthorityCode
import dev.december.jeterbackend.shared.features.clients.data.entities.ClientEntity
import dev.december.jeterbackend.shared.features.clients.domain.models.Client
import dev.december.jeterbackend.shared.features.files.data.entities.FileEntity
import dev.december.jeterbackend.shared.features.files.domain.models.File
import java.time.LocalDate

fun SupplierEntity.supplier(isFavorite: Boolean? = null): Supplier {
    return convert(
        mapOf(
//            "profession" to this.profession?.profession(),
            "files" to this.files.map<FileEntity, File> { it.convert() },
            "passportFiles" to this.passportFiles.map <FileEntity, File> { it.convert() },
            "education" to (this.education?.map { it.education() }?.toSet()),
            "certificate" to (this.certificate?.map { it.certificate() }?.toSet()),
            "isFavorite" to isFavorite,
            "experience" to (LocalDate.now().year - (this.experience?.year ?: LocalDate.now().year))
        )
    )
}

fun EducationEntity.education(): SupplierEducationResponse {
    return this.convert(
        mapOf(
            "file" to this.file?.convert<FileEntity, File>(),
        )
    )
}
fun CertificateEntity.certificate(): SupplierCertificateResponse {
    return this.convert(
        mapOf(
            "file" to this.file?.convert<FileEntity, File>(),
        )
    )
}

//fun UserEntity.client(): UserClient {
//    val client = this.client
//    return convert(
//        mapOf(
//            "id" to client?.id,
//            "sessionCount" to client?.sessionCount,
//            "expenses" to client?.expenses,
//            "avatar" to client?.avatar,
//            "enableStatus" to client?.enableStatus,
//            "user" to this.user()
//        )
//    )
//}