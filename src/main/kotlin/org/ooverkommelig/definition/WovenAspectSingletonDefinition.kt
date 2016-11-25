package org.ooverkommelig.definition

import org.ooverkommelig.Definition
import org.ooverkommelig.SubGraphDefinition

internal class WovenAspectSingletonDefinition<TInterface>(
        override val owner: SubGraphDefinition,
        override val name: String,
        override val interfaceClass: Class<TInterface>,
        override val wrappedDefinition: Definition<TInterface>,
        override val delegate: AspectDelegate<TInterface>) :
        WovenAspectDefinition<TInterface>() {

    override val type = "singleton"

    private val valueCreator = SingletonCreator(this, { createProxyIfWrappedAvailable() })

    override fun get() = valueCreator.getOrCreate()
}
