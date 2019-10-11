package kep.mobile.common.domain.model

import kep.mobile.common.data.BASE_URL
import kep.mobile.common.data.URL_PROTOCOL

const val AMPHITHEATRE = "Amphitheatre"
const val SECOND_FLOOR = "2e etage"
const val CODELAB = "Salle Codelab"
const val WORKSHOP = "Salle Workshop"

val rooms = mapOf(AMPHITHEATRE to listOf("ouverture", "spring", "declarative-ui", "androidx-coroutines", "fullstack", "gradle-dsl", "cloture"),
        SECOND_FLOOR to listOf("native-lib", "workflows", "react", "multiplatform-kotlin13", "study-lib-coroutines"),
        CODELAB to listOf("codelab-arrow", "codelab-spring"),
        WORKSHOP to listOf("workshop-coroutines", "workshop-multiplatform", "workshop-cloud")
)

val timeslots = mapOf(
        "09:00" to listOf("workshop-cloud", "workshop-coroutines", "workshop-multiplatform"),
        "13:30" to listOf("ouverture"),
        "14:00" to listOf("native-lib", "spring"),
        "15:20" to listOf("workflows", "declarative-ui", "codelab-arrow"),
        "16:20" to listOf("androidx-coroutines", "react"),
        "17:10" to listOf("codelab-spring", "fullstack", "multiplatform-kotlin13"),
        "18:10" to listOf("gradle-dsl", "study-lib-coroutines"),
        "18:40" to listOf("cloture")
)

expect fun platform(): String
expect fun platformSpecificAbout(): String

val about = """ 
        |Pour sa partie multiplateforme, cette application a été développée a l'aide:
        | - Du language Kotlin
        | - La librairie Ktor
        | - La librairie kotlinx.serialization
        |
        |Nous avons pu mutualiser:  
        | - La consommation de notre API        
        | - Le code business (use cases)
        | - La mise en place du pattern MVP
        |
        |Pour la partie ${platform()} nous avons utilisé:
        |${platformSpecificAbout()}
        """.trimMargin()

const val twitterUri = "https://twitter.com/kotlin_paris"
val websiteUri = "${URL_PROTOCOL.name}://$BASE_URL"
const val placeName = "Epitech Paris"
val location = 48.815674 to 2.362804
