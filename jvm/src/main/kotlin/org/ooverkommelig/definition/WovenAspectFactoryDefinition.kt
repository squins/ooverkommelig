package org.ooverkommelig.definition

import org.ooverkommelig.Definition
import org.ooverkommelig.SubGraphDefinition

internal class WovenAspectFactoryDefinition<TInterface>(
        override val owner: SubGraphDefinition,
        override val name: String,
        override val interfaceClass: Class<TInterface>,
        override val wrappedDefinition: Definition<TInterface>,
        override val delegate: AspectDelegate<TInterface>) :
        WovenAspectDefinition<TInterface>() {

    override val type = "factory"

    override fun get() = handleCreation { createProxyIfWrappedAvailable() }
}
