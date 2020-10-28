package org.ooverkommelig.examples.dagger.simple

fun main() {
    CoffeeAppOgd().Graph().use { graph ->
        graph.coffeeApp().run()
    }
}

class CoffeeApp(private val coffeeMaker: CoffeeMaker) : Runnable {
    override fun run() {
        coffeeMaker.brew()
    }
}
