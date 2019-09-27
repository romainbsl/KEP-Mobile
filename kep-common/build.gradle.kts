import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("kotlin-multiplatform")
    id("kotlinx-serialization")
}
repositories {
    mavenCentral()
}
kotlin {
    targets {
        jvm("android") {
            tasks.withType<KotlinCompile> {
                kotlinOptions {
                    jvmTarget = "1.8"
                }
            }
        }
        //select iOS target platform depending on the Xcode environment variables
        val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
            if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
                ::iosArm64
            else
                ::iosX64

        iOSTarget("ios") {
            binaries {
                framework {
                    baseName = "SharedCode"
                }
            }
        }

        sourceSets {
            // kotlin libraries
            val coroutinesVersion: String by extra
            val ktorVersion: String by extra
            val serializationVersion: String by extra
            val commonMain by getting {
                dependencies {
                    implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutinesVersion")
                    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serializationVersion")

                    implementation("io.ktor:ktor-client-core:$ktorVersion")
                    implementation("io.ktor:ktor-client-json:$ktorVersion")
                    implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                }
            }
            val androidMain by getting {
                dependencies {
                    implementation("org.jetbrains.kotlin:kotlin-stdlib")
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVersion")

                    implementation("io.ktor:ktor-client-core-jvm:$ktorVersion")
                    implementation("io.ktor:ktor-client-json-jvm:$ktorVersion")
                    implementation("io.ktor:ktor-client-serialization-jvm:$ktorVersion")
                    implementation("io.ktor:ktor-client-apache:$ktorVersion")
                }
            }
            val iosMain by getting {
                dependencies {
                    implementation("ororg.jetbrains.kotlinx:kotlinx-coroutines-core-native:$coroutinesVersion")
                    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$serializationVersion")

                    implementation("io.ktor:ktor-client-ios:$ktorVersion")
                    implementation("io.ktor:ktor-client-core-ios:$ktorVersion")
                    implementation("io.ktor:ktor-client-json-ios:$ktorVersion")
                    implementation("io.ktor:ktor-client-serialization-native:$ktorVersion")
                }
            }
        }
    }
}

val packForXcode by tasks.creating(Sync::class) {
    val targetDir = File(buildDir, "xcode-frameworks")

    /// selecting the right configuration for the iOS
    /// framework depending on the environment
    /// variables set by Xcode build
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets
        .getByName<KotlinNativeTarget>("ios")
        .binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    from({ framework.outputDirectory })
    into(targetDir)

    /// generate a helpful ./gradlew wrapper with embedded Java path
    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText(
            "#!/bin/bash\n"
                    + "export 'JAVA_HOME=${System.getProperty("java.home")}'\n"
                    + "cd '${rootProject.rootDir}'\n"
                    + "./gradlew \$@\n"
        )
        gradlew.setExecutable(true)
    }
}

tasks.getByName("build").dependsOn(packForXcode)