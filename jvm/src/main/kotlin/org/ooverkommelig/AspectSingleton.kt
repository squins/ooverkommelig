package org.ooverkommelig

import org.ooverkommelig.definition.AspectDelegate
import org.ooverkommelig.definition.AspectSingletonDefinition

class AspectSingleton<TInterface>(override val create: (Class<TInterface>, Definition<TInterface>) -> TInterface) :
        AspectDelegate<TInterface>() {
    override fun createDefinition(owner: SubGraphDefinition, name: String) = AspectSingletonDefinition(owner, name, this)
}
