package kep.mobile.common.data

import kep.mobile.common.domain.model.Type
import kotlinx.serialization.Serializable

@Serializable
data class SpeakerEntity(
    val id: String,
    val name: String,
    val company: String? = null,
    val description: String,
    val twitter: String? = null,
    val webSite: String? = null,
    val talks: List<String> = emptyList(),
    val featured: Boolean = false
)

@Serializable
data class TalkEntity(
    val id: String,
    val title: String,
    val description: String,
    val speakers: List<String> = emptyList(),
    val type: Type = Type.TALK
)