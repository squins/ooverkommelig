package org.ooverkommelig.examples.dagger.simple

import org.ooverkommelig.ProvidedAdministration

fun main(args: Array<String>) {
    CoffeeAppOgd(object : CoffeeAppOgd.Provided, ProvidedAdministration() {}).Graph().use { graph ->
        graph.coffeeApp().run()
    }
}

class CoffeeApp(private val coffeeMaker: CoffeeMaker) : Runnable {
    override fun run() {
        coffeeMaker.brew()
    }
}
