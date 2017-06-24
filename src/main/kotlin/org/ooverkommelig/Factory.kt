package org.ooverkommelig

import org.ooverkommelig.definition.FactoryDefinition
import org.ooverkommelig.definition.ObjectCreatingDefinitionDelegate
import kotlin.reflect.KProperty

class Factory<TObject>(internal val create: () -> TObject) : ObjectCreatingDefinitionDelegate<Definition<TObject>, TObject>() {
    override fun registerPropertyIfNeeded(owner: SubGraphDefinition, property: KProperty<*>) {
        owner.addDefinitionProperty(property, false)
    }

    override fun createDefinition(owner: SubGraphDefinition, name: String): Definition<TObject> = FactoryDefinition(owner, name, this)
}
