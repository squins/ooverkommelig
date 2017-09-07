package org.ooverkommelig

import org.ooverkommelig.definition.AspectAlwaysDefinition
import org.ooverkommelig.definition.AspectDelegate

open class AspectAlways<TInterface>(override val create: (Class<TInterface>, Definition<TInterface>) -> TInterface) :
        AspectDelegate<TInterface>() {
    override fun createDefinition(owner: SubGraphDefinition, name: String) = AspectAlwaysDefinition<TInterface>(owner, name, this)
}