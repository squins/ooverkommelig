plugins {
    kotlin("jvm") apply false
    id("io.heapy.gradle.properties")
}

allprojects {
    group = "org.ooverkommelig"
    version = "1-SNAPSHOT"
}

tasks.wrapper {
    gradleVersion = "8.7"
    distributionType = Wrapper.DistributionType.ALL
}
