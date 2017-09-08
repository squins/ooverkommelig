package org.ooverkommelig

import org.ooverkommelig.definition.AlwaysDefinition
import org.ooverkommelig.definition.ObjectCreatingDefinitionSelfProvidingDelegate
import kotlin.reflect.KProperty

open class Always<TObject>(internal val create: () -> TObject) : ObjectCreatingDefinitionSelfProvidingDelegate<Definition<TObject>, TObject>() {
    override fun registerPropertyIfNeeded(owner: SubGraphDefinition, property: KProperty<*>) {
        owner.addDefinitionProperty(property, false)
    }

    override fun createDefinition(owner: SubGraphDefinition, name: String): Definition<TObject> = AlwaysDefinition(owner, name, this)
}
