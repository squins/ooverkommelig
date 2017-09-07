package org.ooverkommelig

import org.ooverkommelig.definition.ObjectCreatingDefinitionDelegate
import org.ooverkommelig.definition.OnceDefinition
import kotlin.reflect.KProperty

open class Once<TObject>(internal val create: () -> TObject) : ObjectCreatingDefinitionDelegate<Definition<TObject>, TObject>() {
    override fun registerPropertyIfNeeded(owner: SubGraphDefinition, property: KProperty<*>) {
        owner.addDefinitionProperty(property, true)
    }

    override fun createDefinition(owner: SubGraphDefinition, name: String): Definition<TObject> = OnceDefinition(owner, name, this)
}