package org.ooverkommelig

import org.ooverkommelig.definition.ObjectCreatingDefinitionDelegate
import org.ooverkommelig.definition.SingletonDefinition
import kotlin.reflect.KProperty

class Singleton<TObject>(internal val create: () -> TObject) : ObjectCreatingDefinitionDelegate<Definition<TObject>, TObject>() {
    override fun registerPropertyIfNeeded(owner: SubGraphDefinition, property: KProperty<*>) {
        owner.addDefinitionProperty(property, true)
    }

    override fun createDefinition(owner: SubGraphDefinition, name: String): Definition<TObject> = SingletonDefinition(owner, name, this)
}
