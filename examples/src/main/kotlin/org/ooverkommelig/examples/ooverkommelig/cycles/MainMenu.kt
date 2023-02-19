package org.ooverkommelig.examples.ooverkommelig.cycles

class MainMenu : Step {
    var eatStep: Step? = null
    var drinkStep: Step? = null

    override fun showInstructions() {
        println("1. Eat something")
        println("2. Drink something")
        println("Q. Quit")
    }

    override fun handleInput(input: String) =
        when (input) {
            "1" -> eatStep
            "2" -> drinkStep
            "q", "Q" -> null
            else -> this
        }
}
