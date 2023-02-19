package org.ooverkommelig.examples.ooverkommelig.cycles

class DrinkMenu : Step {
    var quitStep: Step? = null
    override fun showInstructions() {
        println("1. Drink water")
        println("2. Drink coffee")
        println("3. Drink tea")
        println("Q. Return to main menu")
    }

    override fun handleInput(input: String) =
        when (input) {
            "1" -> drink("water")
            "2" -> drink("coffee")
            "3" -> drink("tea")
            "q", "Q" -> quitStep
            else -> this
        }

    private fun drink(what: String): Step {
        println("Drank $what")
        return this
    }
}
