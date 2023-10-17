plugins {
    `java-library`
    `jvm-test-suite`
    kotlin("jvm")
    `maven-publish`
    id("org.jetbrains.dokka")
    signing
}

val sonatypeUsername: String? by project
val sonatypePassword: String? by project

base {
    archivesName.set("ooverkommelig")
}

repositories {
    mavenCentral()
}

dependencies {
    api(project(":main"))

    implementation(kotlin("reflect"))

    testImplementation(kotlin("test"))
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))

        withJavadocJar()
        withSourcesJar()
    }
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnit()
        }
    }
}

tasks.named("javadocJar", Jar::class) {
    from(tasks.named("dokkaHtml"))
}

publishing {
    publications {
        create<MavenPublication>("jvm") {
            artifactId = "${base.archivesName.get()}-${project.name}"

            from(components["java"])

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
                    url.set("https://github.com/squins/ooverkommelig")
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
