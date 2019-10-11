package kep.mobile.common.data

import kep.mobile.common.FakeData
import kep.mobile.common.domain.model.Type
import kep.mobile.common.domain.model.toModel
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

@UnstableDefault
class TalkEntityTest {

    private val json = """
                    {
                        "id": "androidx-coroutines",
                        "title": "AndroidX + coroutines = <3",
                        "description": "Le framework des coroutines est un outil révolutionnaire pour gérer l'asynchronisme et la concurrence.\nL'intégration avec le SDK d'Android n'est pas forcément évident mais se fait très bien et apporte de nombreux bénéfices.\nAndroidX apporte de nouveaux outils pour simplifier et améliorer cette intégration.\nApprenez comment gérer plus facilement l'asynchronisme dans vos applications.",
                        "speakers": [
                          "geoffrey-metais"
                        ],
                        "type": "TALK"
                    }
        """.trimIndent()

    @Test
    fun `Parse JSON to TalkEntity`() {
        val talkEntity = Json.parse(TalkEntity.serializer(), json)
        assertEquals(talkEntity.id, "androidx-coroutines")
        assertEquals(talkEntity.title, "AndroidX + coroutines = <3")
        assertEquals(talkEntity.speakers, listOf("geoffrey-metais"))
        assertEquals(talkEntity.type, Type.TALK)
    }

    @Test
    fun `TalkEntity to Model`() {
        val talk = FakeData.Entity.androidx_coroutines.toModel(FakeData.Model.speakers)

        assertEquals(talk.id, "androidx-coroutines")
        assertEquals(talk.title, "AndroidX + coroutines = <3")
        assertEquals(talk.content, "Le framework des coroutines est un outil révolutionnaire pour gérer l'asynchronisme et la concurrence.")
        assertEquals(talk.type, Type.TALK)
        assertSame(talk.speakers, FakeData.Model.speakers)
    }
}