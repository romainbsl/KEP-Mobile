buildscript {

    allprojects {
        extra.apply {
            // kotlin
            set("kotlinVersion", "1.3.50")
            // kotlin libraries
            set("coroutinesVersion", "1.3.1")
            set("ktorVersion", "1.2.4")
            set("serializationVersion", "0.13.0")
            // Android
            set("androidGradlePluginVersion", "3.5.0")
        }
    }

    repositories {
        google()
        mavenCentral()
        jcenter()
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
    }

    dependencies {
        val kotlinVersion: String by extra
        val androidGradlePluginVersion: String by extra

        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
        classpath("com.android.tools.build:gradle:$androidGradlePluginVersion")
    }
}

allprojects{
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://dl.bintray.com/jetbrains/kotlin-native-dependencies")
    }
}