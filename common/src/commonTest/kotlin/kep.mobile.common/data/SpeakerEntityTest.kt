package kep.mobile.common.data

import kep.mobile.common.FakeData
import kep.mobile.common.domain.model.toModel
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertSame

@UnstableDefault
class SpeakerEntityTest {
    private val json = """
                      {
                        "id": "geoffrey-metais",
                        "name": "Geoffrey Métais",
                        "company": "VideoLAN",
                        "description": "Lead Android developer at VideoLAN and Videolabs.",
                        "twitter": "geoffreymetais",
                        "webSite": "https://geoffreymetais.github.io/",
                        "talks": [
                          "workshop-coroutines",
                          "androidx-coroutines"
                        ],
                        "featured": false
                      }
        """.trimIndent()

    @Test
    fun `Parse JSON to SpeakerEntity`() {
        val speakerEntity = Json.parse(SpeakerEntity.serializer(), json)
        assertNotNull(speakerEntity)
        assertEquals(speakerEntity.id, "geoffrey-metais")
        assertEquals(speakerEntity.name, "Geoffrey Métais")
        assertEquals(speakerEntity.description, "Lead Android developer at VideoLAN and Videolabs.")
        assertEquals(speakerEntity.talks, listOf("workshop-coroutines", "androidx-coroutines"))
    }

    @Test
    fun `SpeakerEntity to Model`() {
        val speaker = FakeData.Entity.geoffreymetais.toModel(FakeData.Model.talks)

        assertEquals(speaker.id, "geoffrey-metais")
        assertEquals(speaker.name, "Geoffrey Métais")
        assertEquals(speaker.company, "VideoLAN")
        assertEquals(speaker.description, "Lead Android developer at VideoLAN and Videolabs.")
        assertSame(speaker.talks, FakeData.Model.talks)
    }
}