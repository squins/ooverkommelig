package org.ooverkommelig

import org.ooverkommelig.definition.AspectDelegate
import org.ooverkommelig.definition.AspectOnceDefinition

class AspectOnce<TInterface>(override val create: (Class<TInterface>, Definition<TInterface>) -> TInterface) :
        AspectDelegate<TInterface>() {
    override fun createDefinition(owner: SubGraphDefinition, name: String) = AspectOnceDefinition(owner, name, this)
}