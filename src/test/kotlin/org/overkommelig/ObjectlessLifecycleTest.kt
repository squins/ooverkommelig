package org.overkommelig

import org.junit.Assert
import org.junit.Assert.assertSame
import org.junit.Test
import org.ooverkommelig.ObjectGraphConfiguration
import org.ooverkommelig.ObjectGraphDefinition
import org.ooverkommelig.ObjectGraphLogger
import org.ooverkommelig.SubGraphDefinition
import org.ooverkommelig.definition.ObjectlessLifecycle
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ObjectlessLifecycleTest {
    @Test
    fun initIsNotInvokedWhenSubGraphDefinitionIsCreated() {
        class InitNotInvokedWhenSubGraphDefinitionCreatedTestSgd : SubGraphDefinition() {
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
        class InitNotInvokedWhenObjectGraphDefinitionCreatedTestSgd : SubGraphDefinition() {
            var wasInitOfLifecycleInvoked = false

            init {
                lifecycle("Not invoked during object graph definition creation test", { wasInitOfLifecycleInvoked = true }, {})
            }
        }

        class InitNotInvokedWhenObjectGraphDefinitionCreatedTestOgd : ObjectGraphDefinition() {
            val sgd = add(InitNotInvokedWhenObjectGraphDefinitionCreatedTestSgd())
        }

        val ogd = InitNotInvokedWhenObjectGraphDefinitionCreatedTestOgd()

        assertFalse(ogd.sgd.wasInitOfLifecycleInvoked)
    }

    @Test
    fun initIsInvokedWhenGraphIsCreated() {
        class InitInvokedWhenGraphCreatedTestSgd : SubGraphDefinition() {
            var wasInitOfLifecycleInvoked = false

            init {
                lifecycle("Invoked during graph creation test", { wasInitOfLifecycleInvoked = true }, {})
            }
        }

        class InitInvokedWhenGraphCreatedTestOgd : ObjectGraphDefinition() {
            val sgd = add(InitInvokedWhenGraphCreatedTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = InitInvokedWhenGraphCreatedTestOgd()
        ogd.Graph()

        assertTrue(ogd.sgd.wasInitOfLifecycleInvoked)
    }

    @Test
    fun initsAreInvokedInDefinitionOrder() {
        class InitInvokedWhenGraphCreatedTestSgd : SubGraphDefinition() {
            val initInvocationOrderBuilder = StringBuilder()

            init {
                lifecycle("Invoked first", { initInvocationOrderBuilder.append('a') }, {})
                lifecycle("Invoked second", { initInvocationOrderBuilder.append('b') }, {})
            }
        }

        class InitInvokedWhenGraphCreatedTestOgd : ObjectGraphDefinition() {
            val sgd = add(InitInvokedWhenGraphCreatedTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = InitInvokedWhenGraphCreatedTestOgd()
        ogd.Graph()

        assertEquals("ab", ogd.sgd.initInvocationOrderBuilder.toString())
    }

    @Test
    fun followingInitNotInvokedIfPrecedingFailed() {
        class InitInvokedWhenGraphCreatedTestSgd : SubGraphDefinition() {
            var wasSecondInitInvoked = false

            init {
                lifecycle("Failing init", { throw Exception() }, {})
                lifecycle("Not invoked because preceding failed", { wasSecondInitInvoked = false }, {})
            }
        }

        class InitInvokedWhenGraphCreatedTestOgd : ObjectGraphDefinition() {
            val sgd = add(InitInvokedWhenGraphCreatedTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = InitInvokedWhenGraphCreatedTestOgd()
        try {
            ogd.Graph()
        } catch(e: Exception) {
            // This is expected to happen: The first init fails.
        }

        assertFalse(ogd.sgd.wasSecondInitInvoked)
    }

    @Test
    fun disposeNotInvokedWhenSubGraphDefinitionIsCreated() {
        class DisposeNotInvokedWhenSubGraphDefinitionCreatedTestSgd : SubGraphDefinition() {
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
        class DisposeNotInvokedWhenObjectGraphDefinitionCreatedTestSgd : SubGraphDefinition() {
            var wasDisposeInvoked = false

            init {
                lifecycle("Dispose not invoked when object graph definition created", {}, { wasDisposeInvoked = true })
            }
        }

        class DisposeNotInvokedWhenObjectGraphDefinitionCreatedTestOgd : ObjectGraphDefinition() {
            val sgd = add(DisposeNotInvokedWhenObjectGraphDefinitionCreatedTestSgd())
        }

        val ogd = DisposeNotInvokedWhenObjectGraphDefinitionCreatedTestOgd()

        assertFalse(ogd.sgd.wasDisposeInvoked)
    }

    @Test
    fun disposeNotInvokedWhenGraphIsCreated() {
        class DisposeNotInvokedWhenGraphCreatedTestSgd : SubGraphDefinition() {
            var wasDisposeInvoked = false

            init {
                lifecycle("Dispose not invoked when graph created", {}, { wasDisposeInvoked = true })
            }
        }

        class DisposeNotInvokedWhenGraphCreatedTestOgd : ObjectGraphDefinition() {
            val sgd = add(DisposeNotInvokedWhenGraphCreatedTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = DisposeNotInvokedWhenGraphCreatedTestOgd()
        ogd.Graph()

        assertFalse(ogd.sgd.wasDisposeInvoked)
    }

    @Test
    fun disposeInvokedWhenGraphIsClosed() {
        class DisposeInvokedWhenGraphClosedTestSgd : SubGraphDefinition() {
            var wasDisposeInvoked = false

            init {
                lifecycle("Dispose invoked when graph closed", {}, { wasDisposeInvoked = true })
            }
        }

        class DisposeInvokedWhenGraphClosedTestOgd : ObjectGraphDefinition() {
            val sgd = add(DisposeInvokedWhenGraphClosedTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = DisposeInvokedWhenGraphClosedTestOgd()
        ogd.Graph().use {}

        assertTrue(ogd.sgd.wasDisposeInvoked)
    }

    @Test
    fun disposesAreInvokedInReverseDefinitionOrder() {
        class DisposesInvokedInReverseDefinitionOrderTestSgd : SubGraphDefinition() {
            val disposeInvocationOrderBuilder = StringBuilder()

            init {
                lifecycle("Invoked second", {}, { disposeInvocationOrderBuilder.append('a') })
                lifecycle("Invoked first", {}, { disposeInvocationOrderBuilder.append('b') })
            }
        }

        class DisposesInvokedInReverseDefinitionOrderTestOgd : ObjectGraphDefinition() {
            val sgd = add(DisposesInvokedInReverseDefinitionOrderTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = DisposesInvokedInReverseDefinitionOrderTestOgd()
        ogd.Graph().use {}

        assertEquals("ba", ogd.sgd.disposeInvocationOrderBuilder.toString())
    }

    @Test
    fun disposeIsNotInvokedIfInitFailed() {
        class DisposeNotInvokedIfInitFailedTestSgd : SubGraphDefinition() {
            var wasDisposeInvoked = false

            init {
                lifecycle("Dispose not invoked because init failed", { throw Exception() }, { wasDisposeInvoked = true })
            }
        }

        class DisposeNotInvokedIfInitFailedTestOgd : ObjectGraphDefinition() {
            val sgd = add(DisposeNotInvokedIfInitFailedTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = DisposeNotInvokedIfInitFailedTestOgd()
        try {
            ogd.Graph().use {}
        } catch(e: Exception) {
            // This is expected to happen: The first init fails.
        }

        assertFalse(ogd.sgd.wasDisposeInvoked)
    }

    @Test
    fun disposeOfPrecedingInitIsInvokedIfFollowingInitFails() {
        class DisposeOfPrecedingInitInvokedIfFollowingInitFailsTestSgd : SubGraphDefinition() {
            var wasDisposeOfFirstInitInvoked = false

            init {
                lifecycle("Dispose will be invoked", {}, { wasDisposeOfFirstInitInvoked = true })
                lifecycle("Failing init", { throw Exception() }, {})
            }
        }

        class DisposeOfPrecedingInitInvokedIfFollowingInitFailsTestOgd : ObjectGraphDefinition() {
            val sgd = add(DisposeOfPrecedingInitInvokedIfFollowingInitFailsTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = DisposeOfPrecedingInitInvokedIfFollowingInitFailsTestOgd()
        try {
            ogd.Graph().use {}
        } catch(e: Exception) {
            // This is expected to happen: The second init fails.
        }

        assertTrue(ogd.sgd.wasDisposeOfFirstInitInvoked)
    }

    @Test
    fun disposeOfPrecedingLifecycleIsInvokedIfDisposeOfFollowingFails() {
        class DisposeOfPrecedingLifecycleInvokedIfDisposeOfFollowingFailsTestSgd : SubGraphDefinition() {
            var wasDisposeOfFirstInitInvoked = false

            init {
                lifecycle("Dispose will be invoked", {}, { wasDisposeOfFirstInitInvoked = true })
                lifecycle("Failing dispose", {}, { throw Exception() })
            }
        }

        class DisposeOfPrecedingLifecycleInvokedIfDisposeOfFollowingFailsTestOgd : ObjectGraphDefinition() {
            val sgd = add(DisposeOfPrecedingLifecycleInvokedIfDisposeOfFollowingFailsTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = DisposeOfPrecedingLifecycleInvokedIfDisposeOfFollowingFailsTestOgd()
        try {
            ogd.Graph().use {}
        } catch(e: Exception) {
            // This is expected to happen: The second init fails.
        }

        assertTrue(ogd.sgd.wasDisposeOfFirstInitInvoked)
    }

    @Test
    fun errorIsLoggedIfDisposeFails() {
        class DisposeFailureSpy : ObjectGraphLogger {
            var wasErrorLogged = false

            override fun errorDuringCleanUp(sourceObject: Any, operation: String, exception: Exception) {
                wasErrorLogged = true
            }
        }

        val disposeFailureSpy = DisposeFailureSpy()

        class ErrorIsLoggedIfDisposeFailsTestSgd : SubGraphDefinition() {
            init {
                lifecycle("Dispose fails", {}, { throw Exception() })
            }
        }

        class ErrorIsLoggedIfDisposeFailsTestOgd : ObjectGraphDefinition(ObjectGraphConfiguration(disposeFailureSpy)) {
            val sgd = add(ErrorIsLoggedIfDisposeFailsTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = ErrorIsLoggedIfDisposeFailsTestOgd()
        ogd.Graph().use {}

        assertTrue(disposeFailureSpy.wasErrorLogged)
    }

    @Test
    fun sourceObjectIsObjectlessLifecycleIfDisposeFails() {
        class DisposeFailureSpy : ObjectGraphLogger {
            var sourceObject: Any? = null

            override fun errorDuringCleanUp(sourceObject: Any, operation: String, exception: Exception) {
                this.sourceObject = sourceObject
            }
        }

        val disposeFailureSpy = DisposeFailureSpy()

        class ErrorIsLoggedIfDisposeFailsTestSgd : SubGraphDefinition() {
            init {
                lifecycle("Dispose fails", {}, { throw Exception() })
            }
        }

        class ErrorIsLoggedIfDisposeFailsTestOgd : ObjectGraphDefinition(ObjectGraphConfiguration(disposeFailureSpy)) {
            val sgd = add(ErrorIsLoggedIfDisposeFailsTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = ErrorIsLoggedIfDisposeFailsTestOgd()
        ogd.Graph().use {}

        Assert.assertEquals(ObjectlessLifecycle::class.java, disposeFailureSpy.sourceObject?.javaClass)
    }

    @Test
    fun operationIsDisposeIfDisposeFails() {
        class DisposeFailureSpy : ObjectGraphLogger {
            var operation: String? = null

            override fun errorDuringCleanUp(sourceObject: Any, operation: String, exception: Exception) {
                this.operation = operation
            }
        }

        val disposeFailureSpy = DisposeFailureSpy()

        class ErrorIsLoggedIfDisposeFailsTestSgd : SubGraphDefinition() {
            init {
                lifecycle("Dispose fails", {}, { throw Exception() })
            }
        }

        class ErrorIsLoggedIfDisposeFailsTestOgd : ObjectGraphDefinition(ObjectGraphConfiguration(disposeFailureSpy)) {
            val sgd = add(ErrorIsLoggedIfDisposeFailsTestSgd())

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = ErrorIsLoggedIfDisposeFailsTestOgd()
        ogd.Graph().use {}

        assertEquals("dispose", disposeFailureSpy.operation)
    }

    @Test
    fun exceptionIsNullIfDisposeFails() {
        class DisposeFailureSpy : ObjectGraphLogger {
            var exception: Exception? = null

            override fun errorDuringCleanUp(sourceObject: Any, operation: String, exception: Exception) {
                this.exception = exception
            }
        }

        val disposeFailureSpy = DisposeFailureSpy()
        val exception = Exception()

        // Passing "exception" explicitly because of: https://youtrack.jetbrains.com/issue/KT-8120
        class ErrorIsLoggedIfDisposeFailsTestSgd(private val exception: Exception) : SubGraphDefinition() {
            init {
                lifecycle("Dispose fails", {}, { throw this.exception })
            }
        }

        // Passing "exception" explicitly because of: https://youtrack.jetbrains.com/issue/KT-8120
        class ErrorIsLoggedIfDisposeFailsTestOgd(exception: Exception) : ObjectGraphDefinition(ObjectGraphConfiguration(disposeFailureSpy)) {
            val sgd = add(ErrorIsLoggedIfDisposeFailsTestSgd(exception))

            inner class Graph : DefinitionObjectGraph()
        }

        val ogd = ErrorIsLoggedIfDisposeFailsTestOgd(exception)
        ogd.Graph().use {}

        assertSame(exception, disposeFailureSpy.exception)
    }
}
