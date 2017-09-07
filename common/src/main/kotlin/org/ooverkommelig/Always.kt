package org.ooverkommelig

import org.ooverkommelig.definition.AlwaysDefinition
import org.ooverkommelig.definition.ObjectCreatingDefinitionDelegate
import kotlin.reflect.KProperty

open class Always<TObject>(internal val create: () -> TObject) : ObjectCreatingDefinitionDelegate<Definition<TObject>, TObject>() {
    override fun registerPropertyIfNeeded(owner: SubGraphDefinition, property: KProperty<*>) {
        owner.addDefinitionProperty(property, false)
    }

    override fun createDefinition(owner: SubGraphDefinition, name: String): Definition<TObject> = AlwaysDefinition(owner, name, this)
}
