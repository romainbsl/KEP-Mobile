package kep.mobile.common.domain.model

import kep.mobile.common.data.TalkEntity

data class Talk(
    val id: String,
    val title: String,
    val description: String,
    val speakers: List<Speaker> = emptyList(),
    val type: Type = Type.TALK
)

enum class Type(val prefix: String?) {
    CODELAB("Codelab"),
    TALK(null),
    WORKSHOP("Workshop")
}

fun TalkEntity.toModel(speakers: List<Speaker> = emptyList()) =
    Talk(id, title, description, speakers)