package org.ooverkommelig.definition

import org.ooverkommelig.Definition
import kotlin.reflect.KClass

abstract class AspectDefinition<TInterface: Any> {
    internal abstract val delegate: AspectDelegate<TInterface>

    inline fun <reified TActualInterface: TInterface> weave(wrappedDefinition: Definition<TActualInterface>): Definition<TActualInterface> {
        check(TActualInterface::class != Any::class) { "'Any' passed as the interface class, creation functions in aspect definitions must use 'weave(Class<...>, Definition<...>)'." }
        @Suppress("UNCHECKED_CAST")
        val actualInterfaceClassAsAnyClass = TActualInterface::class as KClass<Any>

        @Suppress("UNCHECKED_CAST")
        val actualInterfaceJavaClass = actualInterfaceClassAsAnyClass.java as Class<TActualInterface>
        return weave(actualInterfaceJavaClass, wrappedDefinition)
    }

    @Suppress("UNCHECKED_CAST")
    fun <TActualInterface: TInterface> weave(interfaceClass: Class<TActualInterface>, wrappedDefinition: Definition<TActualInterface>): Definition<TActualInterface> =
            create(interfaceClass, wrappedDefinition, delegate as AspectDelegate<TActualInterface>)

    internal abstract fun <TActualInterface: TInterface> create(interfaceClass: Class<TActualInterface>, wrappedDefinition: Definition<TActualInterface>, delegateTypedWithInterface: AspectDelegate<TActualInterface>): Definition<TActualInterface>
}
