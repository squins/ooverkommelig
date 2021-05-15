package org.ooverkommelig.definition

import org.ooverkommelig.Definition
import org.ooverkommelig.SubGraphDefinition

internal class WovenAspectOnceDefinition<TInterface: Any>(
        override val owner: SubGraphDefinition,
        override val name: String,
        override val interfaceClass: Class<TInterface>,
        override val wrappedDefinition: Definition<TInterface>,
        override val delegate: AspectDelegate<TInterface>) :
        WovenAspectDefinition<TInterface>() {

    override val type = "once"

    private val valueCreator = OnceCreator(this, null) { createProxyIfWrappedAvailable(owner.getAspectInvocationHandlerFactory()) }

    override fun get() = valueCreator.getOrCreate()
}
