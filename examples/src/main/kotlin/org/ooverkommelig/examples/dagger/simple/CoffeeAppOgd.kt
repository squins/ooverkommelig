package org.ooverkommelig.examples.dagger.simple

import org.ooverkommelig.ObjectGraphDefinition
import org.ooverkommelig.req

class CoffeeAppOgd : ObjectGraphDefinition() {
    inner class Graph : DefinitionObjectGraph() {
        fun coffeeApp() = req(coffeeAppSgd.coffeeApp)
    }

    val coffeeAppSgd = add(CoffeeAppSgd(object : CoffeeAppSgd.Provided {
        override fun coffeeMaker() = coffeeMakerSgd.coffeeMaker
    }))
    val coffeeMakerSgd = add(CoffeeMakerSgd(object : CoffeeMakerSgd.Provided {
        override fun heater() = dripCoffeeSgd.heater
        override fun pump() = pumpSgd.pump
    }))
    val dripCoffeeSgd = add(DripCoffeeSgd())
    val pumpSgd = add(PumpSgd(object : PumpSgd.Provided {
        override fun heater() = dripCoffeeSgd.heater
    }))
}
