package org.ooverkommelig.definition

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.ooverkommelig.AspectSingleton
import org.ooverkommelig.NothingProvidedAdministration
import org.ooverkommelig.ObjectGraphDefinition
import org.ooverkommelig.Singleton
import org.ooverkommelig.SubGraphDefinition
import org.ooverkommelig.req
import java.io.Closeable
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class AspectInvocationHandlerTest {
    private val CLOSEABLE_CLASS = Closeable::class.java

    private val RUNNABLE_CLASS = Runnable::class.java

    private val NO_OPERATION_INVOCATION_HANDLER = InvocationHandler { proxy, method, arguments -> }

    private lateinit var graph: AspectInvocationHandlerTestOgd.Graph

    @Before
    fun createGraph() {
        graph = AspectInvocationHandlerTestOgd().Graph()
    }

    @After
    fun closeGraph() {
        graph.close()
    }

    @Test
    fun proxyDoesNotEqualNull() {
        val proxy = graph.aspectWrappedRunnable()

        @Suppress("ReplaceCallWithComparison")
        assertFalse(proxy.equals(null))
    }

    @Test
    fun proxyDoesNotEqualProxyForDifferentInterface() {
        val proxy = graph.aspectWrappedRunnable()

        assertFalse(proxy == newProxyInstance(CLOSEABLE_CLASS, NO_OPERATION_INVOCATION_HANDLER))
    }

    @Test
    fun proxyDoesNotEqualProxyWithDifferentInvocationHandler() {
        val proxy = graph.aspectWrappedRunnable()

        assertFalse(proxy == newProxyInstance(RUNNABLE_CLASS, NO_OPERATION_INVOCATION_HANDLER))
    }

    @Test
    fun proxyDoesNotEqualProxyWithDifferentWrappedObject() {
        val proxy = graph.aspectWrappedRunnable()
        val proxyForAnotherRunnable = graph.aspectWrappedAnotherRunnable()

        assertFalse(proxy == proxyForAnotherRunnable)
    }

    @Test
    fun proxyEqualsItself() {
        val proxy = graph.aspectWrappedRunnable()

        assertEquals(proxy, proxy)
    }

    @Test
    fun proxyHashCodeEqualsWrappedHashCode() {
        val proxy = graph.aspectWrappedRunnable()
        val wrapped = graph.runnable()

        assertEquals(wrapped.hashCode(), proxy.hashCode())
    }

    @Test
    fun proxyStringRepresentationEqualsWrappedStringRepresentation() {
        val proxy = graph.aspectWrappedRunnable()
        val wrapped = graph.runnable()

        assertEquals(wrapped.toString(), proxy.toString())
    }

    @Suppress("UNCHECKED_CAST")
    private fun <TInterface> newProxyInstance(interfaceClass: Class<TInterface>, invocationHandler: InvocationHandler): TInterface =
            Proxy.newProxyInstance(interfaceClass.classLoader, arrayOf(interfaceClass), invocationHandler) as TInterface
}

private class AspectInvocationHandlerTestSgd : SubGraphDefinition(NothingProvidedAdministration) {
    val runnable by Singleton { Runnable { } }

    val anotherRunnable by Singleton { Runnable { } }

    val aspect by AspectSingleton<Any> { interfaceClass, definition -> req(definition) }

    val aspectWrappedRunnable by Singleton { req(aspect.weave(runnable)) }

    val aspectWrappedAnotherRunnable by Singleton { req(aspect.weave(anotherRunnable)) }
}

private class AspectInvocationHandlerTestOgd : ObjectGraphDefinition(NothingProvidedAdministration) {
    val aspectInvocationHandlerTestSgd = add(AspectInvocationHandlerTestSgd())

    inner class Graph : DefinitionObjectGraph() {
        fun runnable() = req(aspectInvocationHandlerTestSgd.runnable)
        fun aspectWrappedRunnable() = req(aspectInvocationHandlerTestSgd.aspectWrappedRunnable)
        fun aspectWrappedAnotherRunnable() = req(aspectInvocationHandlerTestSgd.aspectWrappedAnotherRunnable)
    }
}
