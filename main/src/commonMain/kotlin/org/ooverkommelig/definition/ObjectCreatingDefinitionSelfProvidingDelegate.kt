package org.ooverkommelig.definition

import org.ooverkommelig.SubGraphDefinition
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class ObjectCreatingDefinitionSelfProvidingDelegate<out TDefinition, TObject> :
    ObjectCreatingDefinitionDelegate<TDefinition, TObject>() {
    operator fun provideDelegate(
        owner: SubGraphDefinition,
        property: KProperty<*>
    ): ReadOnlyProperty<SubGraphDefinition, TDefinition> {
        registerPropertyIfNeeded(owner, property)
        return this
    }
}
