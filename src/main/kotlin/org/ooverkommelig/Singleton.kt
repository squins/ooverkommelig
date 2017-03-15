package org.ooverkommelig

import org.ooverkommelig.definition.ObjectCreatingDefinitionDelegate
import org.ooverkommelig.definition.SingletonDefinition
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class Singleton<TObject>(internal val create: () -> TObject) : ObjectCreatingDefinitionDelegate<Definition<TObject>, TObject>() {
    operator fun provideDelegate(owner: SubGraphDefinition, property: KProperty<*>): ReadOnlyProperty<SubGraphDefinition, Definition<TObject>> {
        owner.addDefinitionProperty(property, true)
        return this
    }

    override fun createDefinition(owner: SubGraphDefinition, name: String): Definition<TObject> = SingletonDefinition(owner, name, this)
}
