package kep.mobile.common.data

import kep.mobile.common.domain.model.Talk
import kep.mobile.common.domain.model.Type
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
        val speakerEntity =
            SpeakerEntity(
                id = "geoffrey-metais",
                name = "Geoffrey Métais",
                company = "VideoLAN",
                description = "Lead Android developer at VideoLAN and Videolabs."
            )


        val talks = listOf(
            Talk(
                id = "androidx-coroutines",
                title = "AndroidX + coroutines = <3",
                description = "Le framework des coroutines est un outil révolutionnaire pour gérer l'asynchronisme et la concurrence.",
                type = Type.TALK
            ),
            Talk(
                id = "workshop-coroutines",
                title = "Les coroutines en détail",
                description = "Les coroutines sont la fonctionalité la plus importante introduite dans Kotlin depuis sa création. Vous avez probablement entendu dire que les coroutines facilitent l'asynchronicité et qu'elles permettent d'écrire ce type de code de manière impérative. Saviez vous que Kotlin fournit une implémentation bas niveau des coroutines très différente de la plupart des autres langages de programmation ?\\nDans ce workshop, nous vous proposons de découvrir les coroutines de Kotlin et d'approfondir sur leur spécificité et leurs possibilités. Nous commencerons par comprendre ce que sont exactement les coroutines et leurs utilisations synchrones et asynchrones. Nous verrons ensuite comment les utiliser dans un contexte multi-thread en fonction de nos besoins, et finirons par explorer des patterns architecturaux avancés comme le Reactive Functional Programming et l'Actor Based Programming, simplifé par les librairies haut-niveau (notamment kotlinx.coroutines).\\nÀ travers cette matinée, nous créerons ensemble une petite application qui nous permettra de mettre en pratique notre progression dans l'univers des coroutines. Cette application pourra être à votre choix en ligne de commande ou sur Android. Le seul prérequis est de connaître au choix le langage Java ou le langage Kotlin.",
                type = Type.WORKSHOP
            )
        )

        val speaker = speakerEntity.toModel(talks)

        assertEquals(speaker.id, "geoffrey-metais")
        assertEquals(speaker.name, "Geoffrey Métais")
        assertEquals(speaker.company, "VideoLAN")
        assertEquals(speaker.description, "Lead Android developer at VideoLAN and Videolabs.")
        assertSame(speaker.talks, talks)
    }

}