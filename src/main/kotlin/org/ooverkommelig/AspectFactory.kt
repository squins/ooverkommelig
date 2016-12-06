package org.ooverkommelig

import org.ooverkommelig.definition.AspectDelegate
import org.ooverkommelig.definition.AspectFactoryDefinition

class AspectFactory<TInterface>(override val create: (Class<TInterface>, Definition<TInterface>) -> TInterface) :
        AspectDelegate<TInterface>() {
    override fun createDefinition(owner: SubGraphDefinition, name: String) = AspectFactoryDefinition<TInterface>(owner, name, this)
}
