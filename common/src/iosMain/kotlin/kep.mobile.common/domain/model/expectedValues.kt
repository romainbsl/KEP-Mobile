package kep.mobile.common.domain.model

actual fun platform(): String = "iOS"
actual fun platformSpecificAbout(): String = """
    | - Le language Swift
    """.trimMargin()