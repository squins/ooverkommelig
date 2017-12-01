package org.ooverkommelig

import org.ooverkommelig.definition.ObjectlessLifecycle
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ObjectlessLifecycleTest {
    @Test
    fun initIsNotInvokedWhenSubGraphDefinitionIsCreated() {
        class InitNotInvokedWhenSubGraphDefinitionCreatedTestSgd : SubGraphDefinition(NothingProvidedAdministration) {
            var wasInitOfLifecycleInvoked = false

            init {
                lifecycle("Not invoked during sub graph definition creation test", { wasInitOfLifecycleInvoked = true }, {})
            }
        }

        val sgd = InitNotInvokedWhenSubGraphDefinitionCreatedTestSgd()

        assertFalse(sgd.wasInitOfLifecycleInvoked)
    }

    @Test
    fun initIsNotInvokedWhenObjectGraphDefinitionIsCreated() {
        class InitNotInvokedWhenObjectGraphDefinitionCreatedTestSgd : SubGraphDefinition(NothingProvidedAdministration) {
            var wasInitOfLifecycleInvoked = false

            init {
                lifecycle("Not invoked during object graph definition creation test", { wasInitOfLifecycleInvoked = true }, {})
            }
        }

        class InitNotInvokedWhenObjectGraphDefinitionCreatedTestOgd : ObjectGraphDefinition(NothingProvidedAdministration) {
            val sgd = add(InitNotInvokedWhenObjectGraphDefinitionCreatedTestSgd())
        }

        val ogd = InitNotInvokedWhenObjectGraphDefinitionCreatedTestOgd()

        assertFalse(ogd.sgd.wasInitOfLifecycleInvoked)
    }

    @Test
    fun initIsInvokedWhenGraphIsCreated() {
        class InitInvokedWhenGraphCreatedTestSgd : SubGraphDefinition(NothingProvidedAdministration) {
            var wasInitOfLifecycleInvoked = false

            init {
                lifecycle("Invoked during graph creation test", { wasInitOfLifecycleInvoked = true }, {})
            }
        }

        class InitInvokedWhenGraphCreatedTestOgd : ObjectGraphDefinition(NothingProvidedAdministration) {
            val sgd = add(InitInvokedWhenGraphCreatedTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = InitInvokedWhenGraphCreatedTestOgd()
        ogd.Graph()

        assertTrue(ogd.sgd.wasInitOfLifecycleInvoked)
    }

    @Test
    fun initsAreInvokedInDefinitionOrder() {
        class InitInvokedWhenGraphCreatedTestSgd : SubGraphDefinition(NothingProvidedAdministration) {
            val initInvocationOrderBuilder = StringBuilder()

            init {
                lifecycle("Invoked first", { initInvocationOrderBuilder.append('a') }, {})
                lifecycle("Invoked second", { initInvocationOrderBuilder.append('b') }, {})
            }
        }

        class InitInvokedWhenGraphCreatedTestOgd : ObjectGraphDefinition(NothingProvidedAdministration) {
            val sgd = add(InitInvokedWhenGraphCreatedTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = InitInvokedWhenGraphCreatedTestOgd()
        ogd.Graph()

        assertEquals("ab", ogd.sgd.initInvocationOrderBuilder.toString())
    }

    @Test
    fun followingInitNotInvokedIfPrecedingFailed() {
        class InitInvokedWhenGraphCreatedTestSgd : SubGraphDefinition(NothingProvidedAdministration) {
            var wasSecondInitInvoked = false

            init {
                lifecycle("Failing init", { throw Exception() }, {})
                lifecycle("Not invoked because preceding failed", { wasSecondInitInvoked = false }, {})
            }
        }

        class InitInvokedWhenGraphCreatedTestOgd : ObjectGraphDefinition(NothingProvidedAdministration) {
            val sgd = add(InitInvokedWhenGraphCreatedTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = InitInvokedWhenGraphCreatedTestOgd()
        try {
            ogd.Graph()
        } catch (e: Exception) {
            // This is expected to happen: The first init fails.
        }

        assertFalse(ogd.sgd.wasSecondInitInvoked)
    }

    @Test
    fun disposeNotInvokedWhenSubGraphDefinitionIsCreated() {
        class DisposeNotInvokedWhenSubGraphDefinitionCreatedTestSgd : SubGraphDefinition(NothingProvidedAdministration) {
            var wasDisposeInvoked = false

            init {
                lifecycle("Dispose not invoked when sub graph definition created", {}, { wasDisposeInvoked = true })
            }
        }

        val sgd = DisposeNotInvokedWhenSubGraphDefinitionCreatedTestSgd()

        assertFalse(sgd.wasDisposeInvoked)
    }

    @Test
    fun disposeNotInvokedWhenObjectGraphDefinitionIsCreated() {
        class DisposeNotInvokedWhenObjectGraphDefinitionCreatedTestSgd : SubGraphDefinition(NothingProvidedAdministration) {
            var wasDisposeInvoked = false

            init {
                lifecycle("Dispose not invoked when object graph definition created", {}, { wasDisposeInvoked = true })
            }
        }

        class DisposeNotInvokedWhenObjectGraphDefinitionCreatedTestOgd : ObjectGraphDefinition(NothingProvidedAdministration) {
            val sgd = add(DisposeNotInvokedWhenObjectGraphDefinitionCreatedTestSgd())
        }

        val ogd = DisposeNotInvokedWhenObjectGraphDefinitionCreatedTestOgd()

        assertFalse(ogd.sgd.wasDisposeInvoked)
    }

    @Test
    fun disposeNotInvokedWhenGraphIsCreated() {
        class DisposeNotInvokedWhenGraphCreatedTestSgd : SubGraphDefinition(NothingProvidedAdministration) {
            var wasDisposeInvoked = false

            init {
                lifecycle("Dispose not invoked when graph created", {}, { wasDisposeInvoked = true })
            }
        }

        class DisposeNotInvokedWhenGraphCreatedTestOgd : ObjectGraphDefinition(NothingProvidedAdministration) {
            val sgd = add(DisposeNotInvokedWhenGraphCreatedTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = DisposeNotInvokedWhenGraphCreatedTestOgd()
        ogd.Graph()

        assertFalse(ogd.sgd.wasDisposeInvoked)
    }

    @Test
    fun disposeInvokedWhenGraphIsClosed() {
        class DisposeInvokedWhenGraphClosedTestSgd : SubGraphDefinition(NothingProvidedAdministration) {
            var wasDisposeInvoked = false

            init {
                lifecycle("Dispose invoked when graph closed", {}, { wasDisposeInvoked = true })
            }
        }

        class DisposeInvokedWhenGraphClosedTestOgd : ObjectGraphDefinition(NothingProvidedAdministration) {
            val sgd = add(DisposeInvokedWhenGraphClosedTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = DisposeInvokedWhenGraphClosedTestOgd()
        ogd.Graph().close()

        assertTrue(ogd.sgd.wasDisposeInvoked)
    }

    @Test
    fun disposesAreInvokedInReverseDefinitionOrder() {
        class DisposesInvokedInReverseDefinitionOrderTestSgd : SubGraphDefinition(NothingProvidedAdministration) {
            val disposeInvocationOrderBuilder = StringBuilder()

            init {
                lifecycle("Invoked second", {}, { disposeInvocationOrderBuilder.append('a') })
                lifecycle("Invoked first", {}, { disposeInvocationOrderBuilder.append('b') })
            }
        }

        class DisposesInvokedInReverseDefinitionOrderTestOgd : ObjectGraphDefinition(NothingProvidedAdministration) {
            val sgd = add(DisposesInvokedInReverseDefinitionOrderTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = DisposesInvokedInReverseDefinitionOrderTestOgd()
        ogd.Graph().close()

        assertEquals("ba", ogd.sgd.disposeInvocationOrderBuilder.toString())
    }

    @Test
    fun disposeIsNotInvokedIfInitFailed() {
        class DisposeNotInvokedIfInitFailedTestSgd : SubGraphDefinition(NothingProvidedAdministration) {
            var wasDisposeInvoked = false

            init {
                lifecycle("Dispose not invoked because init failed", { throw Exception() }, { wasDisposeInvoked = true })
            }
        }

        class DisposeNotInvokedIfInitFailedTestOgd : ObjectGraphDefinition(NothingProvidedAdministration) {
            val sgd = add(DisposeNotInvokedIfInitFailedTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = DisposeNotInvokedIfInitFailedTestOgd()
        try {
            ogd.Graph().close()
        } catch (e: Exception) {
            // This is expected to happen: The first init fails.
        }

        assertFalse(ogd.sgd.wasDisposeInvoked)
    }

    @Test
    fun disposeOfPrecedingInitIsInvokedIfFollowingInitFails() {
        class DisposeOfPrecedingInitInvokedIfFollowingInitFailsTestSgd : SubGraphDefinition(NothingProvidedAdministration) {
            var wasDisposeOfFirstInitInvoked = false

            init {
                lifecycle("Dispose will be invoked", {}, { wasDisposeOfFirstInitInvoked = true })
                lifecycle("Failing init", { throw Exception() }, {})
            }
        }

        class DisposeOfPrecedingInitInvokedIfFollowingInitFailsTestOgd : ObjectGraphDefinition(NothingProvidedAdministration) {
            val sgd = add(DisposeOfPrecedingInitInvokedIfFollowingInitFailsTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = DisposeOfPrecedingInitInvokedIfFollowingInitFailsTestOgd()
        try {
            ogd.Graph().close()
        } catch (e: Exception) {
            // This is expected to happen: The second init fails.
        }

        assertTrue(ogd.sgd.wasDisposeOfFirstInitInvoked)
    }

    @Test
    fun disposeOfPrecedingLifecycleIsInvokedIfDisposeOfFollowingFails() {
        class DisposeOfPrecedingLifecycleInvokedIfDisposeOfFollowingFailsTestSgd : SubGraphDefinition(NothingProvidedAdministration) {
            var wasDisposeOfFirstInitInvoked = false

            init {
                lifecycle("Dispose will be invoked", {}, { wasDisposeOfFirstInitInvoked = true })
                lifecycle("Failing dispose", {}, { throw Exception() })
            }
        }

        class DisposeOfPrecedingLifecycleInvokedIfDisposeOfFollowingFailsTestOgd : ObjectGraphDefinition(NothingProvidedAdministration) {
            val sgd = add(DisposeOfPrecedingLifecycleInvokedIfDisposeOfFollowingFailsTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = DisposeOfPrecedingLifecycleInvokedIfDisposeOfFollowingFailsTestOgd()
        try {
            ogd.Graph().close()
        } catch (e: Exception) {
            // This is expected to happen: The second init fails.
        }

        assertTrue(ogd.sgd.wasDisposeOfFirstInitInvoked)
    }

    @Test
    fun errorIsLoggedIfDisposeFails() {
        class DisposeFailureWasErrorLoggedSpy : ObjectGraphLogger {
            var wasErrorLogged = false

            override fun errorDuringCleanUp(sourceObject: Any, operation: String, exception: Exception) {
                wasErrorLogged = true
            }
        }

        val disposeFailureSpy = DisposeFailureWasErrorLoggedSpy()

        class ErrorIsLoggedIfDisposeFailsTestSgd : SubGraphDefinition(NothingProvidedAdministration) {
            init {
                lifecycle("Dispose fails", {}, { throw Exception() })
            }
        }

        class ErrorIsLoggedIfDisposeFailsTestOgd : ObjectGraphDefinition(NothingProvidedAdministration, ObjectGraphConfiguration(disposeFailureSpy)) {
            val sgd = add(ErrorIsLoggedIfDisposeFailsTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = ErrorIsLoggedIfDisposeFailsTestOgd()
        ogd.Graph().close()

        assertTrue(disposeFailureSpy.wasErrorLogged)
    }

    @Test
    fun sourceObjectIsObjectlessLifecycleIfDisposeFails() {
        class DisposeFailureSourceSpy : ObjectGraphLogger {
            var sourceObject: Any? = null

            override fun errorDuringCleanUp(sourceObject: Any, operation: String, exception: Exception) {
                this.sourceObject = sourceObject
            }
        }

        val disposeFailureSpy = DisposeFailureSourceSpy()

        class SourceObjectIsObjectlessLifecycleIfDisposeFailsTestSgd : SubGraphDefinition(NothingProvidedAdministration) {
            init {
                lifecycle("Dispose fails", {}, { throw Exception() })
            }
        }

        class SourceObjectIsObjectlessLifecycleIfDisposeFailsTestOgd : ObjectGraphDefinition(NothingProvidedAdministration, ObjectGraphConfiguration(disposeFailureSpy)) {
            val sgd = add(SourceObjectIsObjectlessLifecycleIfDisposeFailsTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = SourceObjectIsObjectlessLifecycleIfDisposeFailsTestOgd()
        ogd.Graph().close()

        assertEquals(ObjectlessLifecycle::class, disposeFailureSpy.sourceObject?.let { it::class })
    }

    @Test
    fun operationIsDisposeIfDisposeFails() {
        class DisposeFailureOperationSpy : ObjectGraphLogger {
            var operation: String? = null

            override fun errorDuringCleanUp(sourceObject: Any, operation: String, exception: Exception) {
                this.operation = operation
            }
        }

        val disposeFailureSpy = DisposeFailureOperationSpy()

        class OperationIsDisposeIfDisposeFailsestSgd : SubGraphDefinition(NothingProvidedAdministration) {
            init {
                lifecycle("Dispose fails", {}, { throw Exception() })
            }
        }

        class OperationIsDisposeIfDisposeFailsTestOgd : ObjectGraphDefinition(NothingProvidedAdministration, ObjectGraphConfiguration(disposeFailureSpy)) {
            val sgd = add(OperationIsDisposeIfDisposeFailsestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = OperationIsDisposeIfDisposeFailsTestOgd()
        ogd.Graph().close()

        assertEquals("dispose", disposeFailureSpy.operation)
    }

    @Test
    fun exceptionIsNullIfDisposeFails() {
        class DisposeFailureExceptionSpy : ObjectGraphLogger {
            var exception: Exception? = null

            override fun errorDuringCleanUp(sourceObject: Any, operation: String, exception: Exception) {
                this.exception = exception
            }
        }

        val disposeFailureSpy = DisposeFailureExceptionSpy()
        val exception = Exception()

        // Passing "exception" explicitly because of: https://youtrack.jetbrains.com/issue/KT-8120
        class ExceptionIsNullIfDisposeFailsTestSgd(private val exception: Exception) : SubGraphDefinition(NothingProvidedAdministration) {
            init {
                lifecycle("Dispose fails", {}, { throw this.exception })
            }
        }

        // Passing "exception" explicitly because of: https://youtrack.jetbrains.com/issue/KT-8120
        class ExceptionIsNullIfDisposeFailsTestOgd(exception: Exception) : ObjectGraphDefinition(NothingProvidedAdministration, ObjectGraphConfiguration(disposeFailureSpy)) {
            val sgd = add(ExceptionIsNullIfDisposeFailsTestSgd(exception))

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = ExceptionIsNullIfDisposeFailsTestOgd(exception)
        ogd.Graph().close()

        assertTrue(exception === disposeFailureSpy.exception)
    }
}
