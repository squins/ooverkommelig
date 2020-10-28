package org.ooverkommelig.examples.ooverkommelig.homepage

class SomeService(private val log: Log) {
    fun doYourThing() {
        println("Doing my thing")
        log.log("Hello")
    }
}
