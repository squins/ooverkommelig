package org.ooverkommelig

import org.ooverkommelig.definition.AspectAlwaysDefinition
import org.ooverkommelig.definition.AspectDelegate

class AspectAlways<TInterface : Any>(override val create: (Class<TInterface>, Definition<TInterface>) -> TInterface) :
    AspectDelegate<TInterface>() {
    override fun createDefinition(owner: SubGraphDefinition, name: String): AspectAlwaysDefinition<TInterface> {
        check(owner.supportsAspects())

        return AspectAlwaysDefinition(owner, name, this)
    }
}
