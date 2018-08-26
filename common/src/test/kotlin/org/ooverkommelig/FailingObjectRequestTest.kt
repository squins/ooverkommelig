package org.ooverkommelig

import kotlin.test.Test
import kotlin.test.assertTrue

class FailingObjectRequestTest {
    @Test
    fun objectRequestFailureInWireResultsInOriginalExceptionBeingThrown() {
        // Passing "exception" explicitly because of: https://youtrack.jetbrains.com/issue/KT-8120
        class ObjectRequestFailureInWireResultsInOriginalExceptionThrownTestSgd(private val exception: Exception) : SubGraphDefinition(NothingProvidedAdministration) {
            val definitionRequestingFailingDefinitionInWire by Once { SOME_OBJECT }
                    .eager()
                    .wire { req(failingDefinition) }

            val failingDefinition by Once {
                throw exception
            }
        }

        class ObjectRequestFailureInWireResultsInOriginalExceptionThrownTestOgd(exception: Exception) : ObjectGraphDefinition(NothingProvidedAdministration) {
            val main: ObjectRequestFailureInWireResultsInOriginalExceptionThrownTestSgd = add(ObjectRequestFailureInWireResultsInOriginalExceptionThrownTestSgd(exception))

            inner class Graph : DefinitionObjectGraph()
        }

        val exception = Exception()
        val objectGraphDefinition = ObjectRequestFailureInWireResultsInOriginalExceptionThrownTestOgd(exception)

        try {
            objectGraphDefinition.Graph()
            throw IllegalStateException("Expected exception to be thrown.")
        } catch (graphCreationException: Exception) {
            assertTrue(exception === graphCreationException)
        }
    }

    companion object {
        private const val SOME_OBJECT = ""
    }
}
