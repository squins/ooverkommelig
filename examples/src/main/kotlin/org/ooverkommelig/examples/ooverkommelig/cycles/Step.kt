package org.ooverkommelig.examples.ooverkommelig.cycles

interface Step {
    fun showInstructions()

    fun handleInput(input: String): Step?
}
