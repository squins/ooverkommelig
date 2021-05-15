package org.ooverkommelig.definition

import org.ooverkommelig.AspectInvocationHandlerFactory
import org.ooverkommelig.Definition
import java.lang.reflect.Proxy

internal abstract class WovenAspectDefinition<TInterface: Any> :
        Definition<TInterface>(),
        ObjectCreatingDefinition<TInterface> {

    abstract override val delegate: AspectDelegate<TInterface>

    internal abstract val type: String

    internal abstract val interfaceClass: Class<TInterface>

    internal abstract val wrappedDefinition: Definition<TInterface>

    @Suppress("UNCHECKED_CAST")
    internal fun createProxyIfWrappedAvailable(factory: AspectInvocationHandlerFactory) =
            createProxy(delegate.create(interfaceClass, wrappedDefinition), factory)

    @Suppress("UNCHECKED_CAST")
    private fun createProxy(wrapped: TInterface, factory: AspectInvocationHandlerFactory) =
            Proxy.newProxyInstance(interfaceClass.javaClass.classLoader, arrayOf(interfaceClass), factory.create(wrapped, delegate.functions)) as TInterface

    override fun toString() = "Aspect $type definition $name"
}
