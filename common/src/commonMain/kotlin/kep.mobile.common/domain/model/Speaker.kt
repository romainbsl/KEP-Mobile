package kep.mobile.common.domain.model

import kep.mobile.common.data.SpeakerEntity

data class Speaker(
    val id: String,
    val name: String,
    val company: String? = null,
    val description: String,
    val twitter: String? = null,
    val webSite: String? = null,
    val talks: List<Talk> = emptyList(),
    val featured: Boolean = false
)

fun SpeakerEntity.toModel(talks: List<Talk> = emptyList()) =
    Speaker(id, name, company, description, twitter, webSite, talks, featured)