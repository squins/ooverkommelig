plugins {
    kotlin("jvm") version "1.8.10" apply false
    kotlin("multiplatform") version "1.8.10" apply false
    id("io.heapy.gradle.properties").version("1.1.2")
    id("org.jetbrains.dokka") version "1.7.20" apply false
}

allprojects {
    group = "org.ooverkommelig"
    version = "1-SNAPSHOT"
}

tasks.wrapper {
    gradleVersion = "8.0.1"
    distributionType = Wrapper.DistributionType.ALL
}
