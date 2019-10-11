package kep.mobile.common.domain.model

actual fun platform(): String = "Android"
actual fun platformSpecificAbout(): String = """
    | - Le language Kotlin
    | - La libriaire Glide
    """.trimMargin()