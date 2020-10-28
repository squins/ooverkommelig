package org.ooverkommelig.examples.ooverkommelig.cycles

fun main() {
    val inputReader = System.`in`.bufferedReader()
    CyclesOgd().Graph().use { graph ->
        var currentStep: Step? = graph.mainMenu()
        while (currentStep != null) {
            currentStep.showInstructions()
            currentStep = currentStep.handleInput(inputReader.readLine())
        }
    }
}
