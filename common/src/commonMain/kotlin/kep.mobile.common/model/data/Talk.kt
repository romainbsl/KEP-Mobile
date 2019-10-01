package kep.mobile.common.model.data

import kotlinx.serialization.Serializable

@Serializable
data class Talk(
    val id: String,
    val title: String,
    val description: String,
    val speakers: List<String>,
    val type: Type = Type.TALK
) {
    enum class Type(val prefix: String?) {
        CODELAB("Codelab"),
        TALK(null),
        WORKSHOP("Workshop")
    }
}