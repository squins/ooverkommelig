package org.ooverkommelig.examples.ooverkommelig.cycles

import org.ooverkommelig.ProvidedAdministration

fun main() {
    val inputReader = System.`in`.bufferedReader()
    CyclesOgd(object : CyclesOgd.Provided, ProvidedAdministration() {}).Graph().use { graph ->
        var currentStep: Step? = graph.mainMenu()
        while (currentStep != null) {
            currentStep.showInstructions()
            currentStep = currentStep.handleInput(inputReader.readLine())
        }
    }
}
