package org.ooverkommelig

import org.ooverkommelig.definition.ObjectCreatingDefinitionDelegate
import org.ooverkommelig.definition.SingletonDefinition

class Singleton<TObject>(internal val create: () -> TObject) : ObjectCreatingDefinitionDelegate<Definition<TObject>, TObject>() {
    override fun createDefinition(owner: SubGraphDefinition, name: String): Definition<TObject> = SingletonDefinition(owner, name, this)
}
