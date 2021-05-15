package org.ooverkommelig

import org.ooverkommelig.definition.AspectDelegate
import org.ooverkommelig.definition.AspectOnceDefinition

class AspectOnce<TInterface: Any>(override val create: (Class<TInterface>, Definition<TInterface>) -> TInterface) :
        AspectDelegate<TInterface>() {
    override fun createDefinition(owner: SubGraphDefinition, name: String): AspectOnceDefinition<TInterface> {
        check(owner.supportsAspects())

        return AspectOnceDefinition(owner, name, this)
    }
}