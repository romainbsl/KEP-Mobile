package kep.mobile.common

import kep.mobile.common.data.SpeakerEntity
import kep.mobile.common.data.TalkEntity
import kep.mobile.common.domain.model.Speaker
import kep.mobile.common.domain.model.Talk
import kep.mobile.common.domain.model.Type

object FakeData {
    object Entity {
        val geoffreymetais = SpeakerEntity(
            id = "geoffrey-metais",
            name = "Geoffrey Métais",
            company = "VideoLAN",
            description = "Lead Android developer at VideoLAN and Videolabs.",
            twitter = "geoffreymetais",
            webSite = "https://geoffreymetais.github.io/",
            talks = listOf("workshop-coroutines", "androidx-coroutines"),
            featured = false
        )

        val androidx_coroutines = TalkEntity(
            id = "androidx-coroutines",
            title = "AndroidX + coroutines = <3",
            description = "Le framework des coroutines est un outil révolutionnaire pour gérer l'asynchronisme et la concurrence.",
            speakers = listOf("geoffrey-metais"),
            type = Type.TALK
        )

        val workshop_coroutines = TalkEntity(
            id = "workshop-coroutines",
            title = "Les coroutines en détail",
            description = "Les coroutines sont la fonctionalité la plus importante introduite dans Kotlin depuis sa création. Vous avez probablement entendu dire que les coroutines facilitent l'asynchronicité et qu'elles permettent d'écrire ce type de code de manière impérative. Saviez vous que Kotlin fournit une implémentation bas niveau des coroutines très différente de la plupart des autres langages de programmation ?\\nDans ce workshop, nous vous proposons de découvrir les coroutines de Kotlin et d'approfondir sur leur spécificité et leurs possibilités. Nous commencerons par comprendre ce que sont exactement les coroutines et leurs utilisations synchrones et asynchrones. Nous verrons ensuite comment les utiliser dans un contexte multi-thread en fonction de nos besoins, et finirons par explorer des patterns architecturaux avancés comme le Reactive Functional Programming et l'Actor Based Programming, simplifé par les librairies haut-niveau (notamment kotlinx.coroutines).\\nÀ travers cette matinée, nous créerons ensemble une petite application qui nous permettra de mettre en pratique notre progression dans l'univers des coroutines. Cette application pourra être à votre choix en ligne de commande ou sur Android. Le seul prérequis est de connaître au choix le langage Java ou le langage Kotlin.",
            speakers = listOf("geoffrey-metais", "louis-cad"),
            type = Type.WORKSHOP
        )

        val speakers = listOf(geoffreymetais)
        val talks = listOf(androidx_coroutines, workshop_coroutines)
    }

    object Model {
        val geoffreymetais = Speaker(
            id = "geoffrey-metais",
            name = "Geoffrey Métais",
            company = "VideoLAN",
            description = "Lead Android developer at VideoLAN and Videolabs.",
            twitter = "geoffreymetais",
            webSite = "https://geoffreymetais.github.io/",
            featured = false
        )
        val androidx_coroutines = Talk(
            id = "androidx-coroutines",
            title = "AndroidX + coroutines = <3",
            description = "Le framework des coroutines est un outil révolutionnaire pour gérer l'asynchronisme et la concurrence.",
            type = Type.TALK
        )

        val workshop_coroutines = Talk(
            id = "workshop-coroutines",
            title = "Les coroutines en détail",
            description = "Les coroutines sont la fonctionalité la plus importante introduite dans Kotlin depuis sa création. Vous avez probablement entendu dire que les coroutines facilitent l'asynchronicité et qu'elles permettent d'écrire ce type de code de manière impérative. Saviez vous que Kotlin fournit une implémentation bas niveau des coroutines très différente de la plupart des autres langages de programmation ?\\nDans ce workshop, nous vous proposons de découvrir les coroutines de Kotlin et d'approfondir sur leur spécificité et leurs possibilités. Nous commencerons par comprendre ce que sont exactement les coroutines et leurs utilisations synchrones et asynchrones. Nous verrons ensuite comment les utiliser dans un contexte multi-thread en fonction de nos besoins, et finirons par explorer des patterns architecturaux avancés comme le Reactive Functional Programming et l'Actor Based Programming, simplifé par les librairies haut-niveau (notamment kotlinx.coroutines).\\nÀ travers cette matinée, nous créerons ensemble une petite application qui nous permettra de mettre en pratique notre progression dans l'univers des coroutines. Cette application pourra être à votre choix en ligne de commande ou sur Android. Le seul prérequis est de connaître au choix le langage Java ou le langage Kotlin.",
            type = Type.WORKSHOP
        )

        val speakers = listOf(geoffreymetais)
        val talks = listOf(androidx_coroutines, workshop_coroutines)
    }

}