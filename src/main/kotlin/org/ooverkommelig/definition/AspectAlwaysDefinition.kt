package org.ooverkommelig.definition

import org.ooverkommelig.Definition
import org.ooverkommelig.SubGraphDefinition

class AspectAlwaysDefinition<TInterface> internal constructor(
        private val owner: SubGraphDefinition,
        private val name: String,
        override val delegate: AspectDelegate<TInterface>) :
        AspectDefinition<TInterface>() {
    override fun <TActualInterface : TInterface> create(interfaceClass: Class<TActualInterface>, wrappedDefinition: Definition<TActualInterface>, delegateTypedWithInterface: AspectDelegate<TActualInterface>) =
            WovenAspectAlwaysDefinition(owner, name, interfaceClass, wrappedDefinition, delegateTypedWithInterface)
}
