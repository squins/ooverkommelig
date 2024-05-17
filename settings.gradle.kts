pluginManagement {
    plugins {
        // https://kotlinlang.org/docs/home.html
        kotlin("jvm") version "1.9.24"
        kotlin("multiplatform") version "1.9.24"

        // https://plugins.gradle.org/plugin/io.heapy.gradle.properties
        id("io.heapy.gradle.properties").version("1.1.2")

        // https://plugins.gradle.org/plugin/org.gradle.toolchains.foojay-resolver-convention
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"

        // https://plugins.gradle.org/plugin/org.jetbrains.dokka
        id("org.jetbrains.dokka") version "1.9.20"
    }

    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention")
}

rootProject.name = "ooverkommelig"

include(":examples")
include(":jvm-reflect")
include(":main")
