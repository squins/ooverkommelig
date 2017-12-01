package org.ooverkommelig.examples.dagger.simple

class ElectricHeater : Heater {
    private var heating: Boolean = false

    override fun on() {
        println("~ ~ ~ heating ~ ~ ~")
        this.heating = true
    }

    override fun off() {
        this.heating = false
    }

    override fun isHot(): Boolean {
        return heating
    }
}
