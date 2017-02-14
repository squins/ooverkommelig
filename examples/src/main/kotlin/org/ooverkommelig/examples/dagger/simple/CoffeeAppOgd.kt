package org.ooverkommelig.examples.dagger.simple

import org.ooverkommelig.ObjectGraphDefinition
import org.ooverkommelig.owner
import org.ooverkommelig.req

class CoffeeAppOgd(provided: Provided) : ObjectGraphDefinition(provided) {
    interface Provided : CoffeeAppSgd.Provided, CoffeeMakerSgd.Provided, DripCoffeeSgd.Provided, PumpSgd.Provided {
        fun coffeeMakerSgd() = owner<CoffeeAppOgd>().coffeeMakerSgd
        override fun coffeeMaker() = coffeeMakerSgd().coffeeMaker

        fun dripCoffeeSgd() = owner<CoffeeAppOgd>().dripCoffeeSgd
        override fun heater() = dripCoffeeSgd().heater

        fun pumpSgd() = owner<CoffeeAppOgd>().pumpSgd
        override fun pump() = pumpSgd().pump
    }

    inner class Graph : DefinitionObjectGraph() {
        fun coffeeApp() = req(coffeeAppSgd.coffeeApp)
    }

    val coffeeAppSgd = add(CoffeeAppSgd(provided))
    val coffeeMakerSgd = add(CoffeeMakerSgd(provided))
    val dripCoffeeSgd = add(DripCoffeeSgd(provided))
    val pumpSgd = add(PumpSgd(provided))
}
