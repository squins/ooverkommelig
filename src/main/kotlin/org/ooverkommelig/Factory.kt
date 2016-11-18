package org.ooverkommelig

import org.ooverkommelig.definition.FactoryDefinition
import org.ooverkommelig.definition.ObjectCreatingDefinitionDelegate

class Factory<TObject>(internal val create: () -> TObject) : ObjectCreatingDefinitionDelegate<Definition<TObject>, TObject>() {
    override fun createDefinition(owner: SubGraphDefinition, name: String): Definition<TObject> = FactoryDefinition(owner, name, this)
}
