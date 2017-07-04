package org.ooverkommelig

import org.junit.Assert.assertSame
import org.junit.Test

class FailingObjectRequestTest {
    @Test
    fun objectRequestFailureInWireResultsInOriginalExceptionBeingThrown() {
        // Passing "exception" explicitly because of: https://youtrack.jetbrains.com/issue/KT-8120
        class ObjectRequestFailureInWireResultsInOriginalExceptionThrownTestSgd(private val exception: Exception) : SubGraphDefinition(NothingProvidedAdministration) {
            val definitionRequestingFailingDefinitionInWire by Singleton { SOME_OBJECT }
                    .wire { req(failingDefinition) }

            val failingDefinition by Singleton {
                throw exception
            }

            override fun objectsToCreateEagerly() = listOf(definitionRequestingFailingDefinitionInWire)
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
        } catch(graphCreationException: Exception) {
            assertSame(exception, graphCreationException)
        }
    }

    companion object {
        private val SOME_OBJECT = ""
    }
}
