package org.ooverkommelig

import org.ooverkommelig.definition.FactoryDefinition
import org.ooverkommelig.definition.ObjectCreatingDefinitionDelegate
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class Factory<TObject>(internal val create: () -> TObject) : ObjectCreatingDefinitionDelegate<Definition<TObject>, TObject>() {
    operator fun provideDelegate(owner: SubGraphDefinition, property: KProperty<*>): ReadOnlyProperty<SubGraphDefinition, Definition<TObject>> {
        owner.addDefinitionProperty(property, false)
        return this
    }

    override fun createDefinition(owner: SubGraphDefinition, name: String): Definition<TObject> = FactoryDefinition(owner, name, this)
}
