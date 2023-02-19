import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName

plugins {
    `java-library`
    kotlin("multiplatform")
    `maven-publish`
    id("org.jetbrains.dokka")
    signing
}

val sonatypeUsername: String? by project
val sonatypePassword: String? by project

val useChromeHeadlessForKarmaTests: String? by project
val useChromiumHeadlessForKarmaTests: String? by project
val useFirefoxHeadlessForKarmaTests: String? by project
val useIeForKarmaTests: String? by project
val useOperaForKarmaTests: String? by project
val useSafariForKarmaTests: String? by project

base {
    archivesName.set("ooverkommelig")
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))

        withJavadocJar()
        withSourcesJar()
    }
}

tasks.named("javadocJar", Jar::class) {
    from(tasks.named("dokkaHtml"))
}

kotlin {
    js(IR) {
        binaries.executable()

        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
            testTask {
                useKarma {
                    // specify which browsers to use in "local.properties". See "local.template.properties".
                    if (useChromeHeadlessForKarmaTests.toBoolean()) {
                        useChromeHeadless()
                    }
                    if (useChromiumHeadlessForKarmaTests.toBoolean()) {
                        useChromiumHeadless()
                    }
                    if (useFirefoxHeadlessForKarmaTests.toBoolean()) {
                        useFirefoxHeadless()
                    }
                    if (useIeForKarmaTests.toBoolean()) {
                        useIe()
                    }
                    if (useOperaForKarmaTests.toBoolean()) {
                        useOpera()
                    }
                    if (useSafariForKarmaTests.toBoolean()) {
                        useSafari()
                    }
                }
            }
        }
    }

    jvm {
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jsMain by getting
        val jsTest by getting
        val jvmMain by getting
        val jvmTest by getting
    }
}

publishing {
    publications {
        withType<MavenPublication> {
            pom {
                name.set("OOverkommelig")
                description.set("Manageable dependency injection for Kotlin.")
                url.set("https://ooverkommelig.org/")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://mit-license.org/")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/squins/ooverkommelig.git")
                    developerConnection.set("scm:git:https://github.com/squins/ooverkommelig.git")
                    url.set("https://github.com/squins/ooverkommelig/")
                }
                developers {
                    developer {
                        id.set("jstuyts")
                        name.set("Johan Stuyts")
                        email.set("j.stuyts@javathinker.com")
                    }
                    developer {
                        id.set("keesvandieren")
                        name.set("Kees van Dieren")
                        email.set("keesvandieren@squins.com")
                    }
                }
            }
        }

        named<MavenPublication>("js") {
            artifactId = "${archivesName.get()}-$name"
        }

        named<MavenPublication>("jvm") {
            artifactId = "${archivesName.get()}-$name"
            artifact(tasks.named("javadocJar"))
        }

        named<MavenPublication>("kotlinMultiplatform") {
            artifactId = archivesName.get()
        }
    }
}

// Put your GPG and Sonatype credentials in "local.properties". See "local.template.properties".
//
// Documentation for GPG:
//     https://docs.gradle.org/current/userguide/signing_plugin.html#sec:signatory_credentials
if (project.hasProperty("signing.keyId") && project.hasProperty("signing.password") && project.hasProperty("signing.secretKeyRingFile") && sonatypeUsername != null && sonatypePassword != null) {
    publishing {
        repositories {
            maven {
                name = "ossSonatype"
                val snapshotsRepoUrl = project.uri("https://s01.oss.sonatype.org/content/repositories/snapshots")
                val releasesRepoUrl = project.uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2")
                url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
                credentials {
                    username = sonatypeUsername
                    password = sonatypePassword
                }
            }
        }
    }

    signing {
        sign(publishing.publications)
    }
}
