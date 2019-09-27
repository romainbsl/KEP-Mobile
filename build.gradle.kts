buildscript {

    allprojects {
        extra.apply {
            // kotlin
            set("kotlinVersion", "1.3.50")
            // kotlin libraries
            set("coroutinesVersion", "1.1.1")
            set("ktorVersion", "1.3.0-beta-1")
            set("serializationVersion", "0.13.0")
            // Android
            set("androidGradlePluginVersion", "3.5.0")
        }
    }

    repositories {
        google()
        mavenCentral()
        jcenter()
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
        jcenter()
    }
}