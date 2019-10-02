import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("kotlin-multiplatform")
    id("kotlinx-serialization")
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
                    baseName = "kep-common"
                }
            }
        }

        sourceSets {
            // kotlin libraries
            val coroutinesVersion: String by extra
            val ktorVersion: String by extra
            val serializationVersion: String by extra

            fun kotlinx(module: String, version: String) = "org.jetbrains.kotlinx:kotlinx-$module:$version"
            fun coroutines(module: String = "") = kotlinx("coroutines-core$module", coroutinesVersion)
            fun serialization(module: String = "") = kotlinx("serialization-runtime$module", serializationVersion)
            fun ktorClient(module: String, version: String = ktorVersion) = "io.ktor:ktor-client-$module:$version"

            val commonMain by getting {
                dependencies {
                    implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
                    implementation(coroutines("-common"))
                    implementation(serialization("-common"))

                    implementation(ktorClient("core"))
                    implementation(ktorClient("json"))
                    implementation(ktorClient("serialization"))
                }
            }
            val commonTest by getting {
                dependencies {
                    implementation(kotlin("test-common"))
                    implementation(kotlin("test-annotations-common"))
                    implementation("io.mockk:mockk:1.9.3")
                }
            }
            val androidMain by getting {
                dependencies {
                    implementation("org.jetbrains.kotlin:kotlin-stdlib")
                    implementation(coroutines())
                    implementation(serialization())

                    implementation(ktorClient("core-jvm"))
                    implementation(ktorClient("json-jvm"))
                    implementation(ktorClient("serialization-jvm"))
                    implementation(ktorClient("apache"))
                }
            }
            val androidTest by getting {
                dependencies {
                    implementation(kotlin("test"))
                    implementation(kotlin("test-junit"))
                    implementation("io.mockk:mockk:1.9.3")
                }
            }
            val iosMain by getting {
                dependencies {
                    implementation(coroutines("-native"))
                    implementation(serialization("-native"))

                    implementation(ktorClient("core-native"))
                    implementation(ktorClient("ios"))
                    implementation(ktorClient("json-native"))
                    implementation(ktorClient("serialization-native"))
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