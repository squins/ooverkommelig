plugins {
    kotlin("jvm") version "1.9.10" apply false
    kotlin("multiplatform") version "1.9.10" apply false
    id("io.heapy.gradle.properties").version("1.1.2")
    id("org.jetbrains.dokka") version "1.9.10" apply false
}

allprojects {
    group = "org.ooverkommelig"
    version = "1-SNAPSHOT"
}

tasks.wrapper {
    gradleVersion = "8.4"
    distributionType = Wrapper.DistributionType.ALL
}
