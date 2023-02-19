package org.ooverkommelig.examples.ooverkommelig.cycles

class EatMenu : Step {
    var quitStep: Step? = null
    override fun showInstructions() {
        println("1. Eat bread")
        println("2. Eat soup")
        println("3. Eat dessert")
        println("Q. Return to main menu")
    }

    override fun handleInput(input: String) =
        when (input) {
            "1" -> eat("bread")
            "2" -> eat("soup")
            "3" -> eat("dessert")
            "q", "Q" -> quitStep
            else -> this
        }

    private fun eat(what: String): Step {
        println("Ate $what")
        return this
    }
}
