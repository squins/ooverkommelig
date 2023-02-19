plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":main"))
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

val runDaggerSimple by tasks.registering(JavaExec::class) {
    group = "Execution"
    description = "Runs the port of the \"simple\" example of Dagger."
    mainClass.set("org.ooverkommelig.examples.dagger.simple.CoffeeAppKt")
    dependsOn(tasks.classes)
    classpath = sourceSets.main.get().runtimeClasspath
    isIgnoreExitValue = true
}

val runGuiceXml by tasks.registering(JavaExec::class) {
    group = "Execution"
    description = "Runs the port of the XML example of Guice."
    mainClass.set("org.ooverkommelig.examples.guice.xml.ContactsAppKt")
    dependsOn(tasks.classes)
    classpath = sourceSets.main.get().runtimeClasspath
    isIgnoreExitValue = true
}

val runOoverkommeligCycles by tasks.registering {
    group = "Execution"
    description = "Runs cycles example."
    doLast {
        println("This example uses standard input, which is not available when running from Gradle. Please run the example from your IDE.")
    }
}

val runOoverkommeligLifecycle by tasks.registering(JavaExec::class) {
    group = "Execution"
    description = "Runs lifecycle example."
    mainClass.set("org.ooverkommelig.examples.ooverkommelig.lifecycle.LifecycleAppKt")
    dependsOn(tasks.classes)
    classpath = sourceSets.main.get().runtimeClasspath
    isIgnoreExitValue = true
}

val runOoverkommeligHomePage by tasks.registering(JavaExec::class) {
    group = "Execution"
    description = "Runs home page example."
    mainClass.set("org.ooverkommelig.examples.ooverkommelig.homepage.HomePageAppKt")
    dependsOn(tasks.classes)
    classpath = sourceSets.main.get().runtimeClasspath
    isIgnoreExitValue = true
}

val runOoverkommeligTypes by tasks.registering(JavaExec::class) {
    group = "Execution"
    description = "Runs types example."
    mainClass.set("org.ooverkommelig.examples.ooverkommelig.types.TypesAppKt")
    dependsOn(tasks.classes)
    classpath = sourceSets.main.get().runtimeClasspath
    isIgnoreExitValue = true
    doFirst {
        println("NOTE: This will (try to) open a dialog window. Gradle will hang until this dialog is closed.")
    }
}
