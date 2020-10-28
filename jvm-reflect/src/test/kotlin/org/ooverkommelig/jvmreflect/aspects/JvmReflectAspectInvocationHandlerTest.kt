package org.ooverkommelig.jvmreflect.aspects

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.ooverkommelig.*
import java.io.Closeable
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class JvmReflectAspectInvocationHandlerTest {
    private lateinit var graph: JvmReflectAspectInvocationHandlerTestOgd.Graph

    @Before
    fun createGraph() {
        graph = JvmReflectAspectInvocationHandlerTestOgd().Graph()
    }

    @After
    fun closeGraph() {
        graph.close()
    }

    @Test
    fun proxyDoesNotEqualNull() {
        val proxy = graph.aspectWrappedRunnable()

        @Suppress("ReplaceCallWithComparison")
        assertNotNull(proxy)
    }

    @Test
    fun proxyDoesNotEqualProxyForDifferentInterface() {
        val proxy = graph.aspectWrappedRunnable()

        assertNotEquals(proxy, newNoOperationProxyInstance(CLOSEABLE_CLASS))
    }

    @Test
    fun proxyDoesNotEqualProxyWithDifferentInvocationHandler() {
        val proxy = graph.aspectWrappedRunnable()

        assertNotEquals(proxy, newNoOperationProxyInstance(RUNNABLE_CLASS))
    }

    @Test
    fun proxyDoesNotEqualProxyWithDifferentWrappedObject() {
        val proxy = graph.aspectWrappedRunnable()
        val proxyForAnotherRunnable = graph.aspectWrappedAnotherRunnable()

        assertNotEquals(proxy, proxyForAnotherRunnable)
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
    private fun <TInterface> newNoOperationProxyInstance(interfaceClass: Class<TInterface>): TInterface =
            Proxy.newProxyInstance(interfaceClass.classLoader, arrayOf(interfaceClass), NO_OPERATION_INVOCATION_HANDLER) as TInterface

    companion object {
        private val CLOSEABLE_CLASS = Closeable::class.java

        private val RUNNABLE_CLASS = Runnable::class.java

        private val NO_OPERATION_INVOCATION_HANDLER = InvocationHandler { _, _, _ -> }
    }
}

private class JvmReflectAspectInvocationHandlerTestSgd(objectGraphConfiguration: ObjectGraphConfiguration) : SubGraphDefinition(objectGraphConfiguration) {
    val runnable by Once { Runnable { } }

    val anotherRunnable by Once { Runnable { } }

    val aspect by AspectOnce<Any> { _, definition -> req(definition) }

    val aspectWrappedRunnable by Once { req(aspect.weave(runnable)) }

    val aspectWrappedAnotherRunnable by Once { req(aspect.weave(anotherRunnable)) }
}

private val objectGraphConfiguration = ObjectGraphConfiguration(aspectInvocationHandlerFactory = JvmReflectAspectInvocationHandlerFactory)

private class JvmReflectAspectInvocationHandlerTestOgd : ObjectGraphDefinition(objectGraphConfiguration) {
    val jvmReflectAspectInvocationHandlerTestSgd = add(JvmReflectAspectInvocationHandlerTestSgd(objectGraphConfiguration))

    inner class Graph : DefinitionObjectGraph() {
        fun runnable() = req(jvmReflectAspectInvocationHandlerTestSgd.runnable)
        fun aspectWrappedRunnable() = req(jvmReflectAspectInvocationHandlerTestSgd.aspectWrappedRunnable)
        fun aspectWrappedAnotherRunnable() = req(jvmReflectAspectInvocationHandlerTestSgd.aspectWrappedAnotherRunnable)
    }
}
