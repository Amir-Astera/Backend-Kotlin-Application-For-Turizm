package dev.december.jeterbackend.shared.features.suppliers.data.entiies

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity(name = "social_media")
data class SocialMediaEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: String = UUID.randomUUID().toString(),
    @OneToOne(mappedBy = "socialMedias")
    private val supplier: SupplierEntity? = null,
    @Column(name = "linkedin")
    val linkedin: String? = null,
    @Column(name = "facebook")
    val facebook: String? = null,
    @Column(name = "instagram")
    val instagram: String? = null,
    @Column(name = "youtube")
    val youtube: String? = null,
    @Column(name = "tiktok")
    val tiktok: String? = null,
    @Column(name = "telegram")
    val telegram: String? = null,
    @Column(name = "twitter")
    val twitter: String? = null,
    @Column(name = "vk")
    val vk: String? = null
)