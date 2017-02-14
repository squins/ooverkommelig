package org.ooverkommelig.examples.dagger.simple

class CoffeeMaker(private val heater: Lazy<Heater>, private val pump: Pump) {
    fun brew() {
        heater.value.on()
        pump.pump()
        println(" [_]P coffee! [_]P ")
        heater.value.off()
    }
}
