package kep.mobile.common.model.data

import kotlinx.serialization.Serializable

@Serializable
data class Speaker(
    val id: String,
    val name: String,
    val company: String? = null,
    val description: String,
    val twitter: String? = null,
    val webSite: String? = null,
    val talks: List<String> = emptyList(),
    val featured: Boolean = false
)