package org.ooverkommelig.definition

import org.ooverkommelig.Definition
import java.lang.reflect.Proxy

internal abstract class WovenAspectDefinition<TInterface> :
        Definition<TInterface>(),
        ObjectCreatingDefinition<TInterface> {

    abstract override val delegate: AspectDelegate<TInterface>

    internal abstract val type: String

    internal abstract val interfaceClass: Class<TInterface>

    internal abstract val wrappedDefinition: Definition<TInterface>

    @Suppress("UNCHECKED_CAST")
    internal fun createProxyIfWrappedAvailable() = (delegate.create(interfaceClass, wrappedDefinition)?.let { wrapped -> createProxy(wrapped as Any) }) as TInterface

    @Suppress("UNCHECKED_CAST")
    private fun createProxy(wrapped: Any) = Proxy.newProxyInstance(interfaceClass.javaClass.classLoader, arrayOf(interfaceClass), AspectInvocationHandler(wrapped, delegate)) as TInterface

    override fun toString() = "Aspect $type definition $name"
}
