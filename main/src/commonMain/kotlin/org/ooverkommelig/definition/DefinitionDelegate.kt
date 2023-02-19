package org.ooverkommelig.definition

import org.ooverkommelig.SubGraphDefinition
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class DefinitionDelegate<out TDefinition> : ReadOnlyProperty<SubGraphDefinition, TDefinition> {
    private var definition: TDefinition? = null

    override operator fun getValue(thisRef: SubGraphDefinition, property: KProperty<*>) =
        getValue(thisRef, property.name)

    internal fun getValue(owner: SubGraphDefinition, propertyName: String): TDefinition {
        val currentDefinition = definition ?: createDefinition(owner, "${owner.name}#$propertyName")
        definition = currentDefinition
        return currentDefinition
    }

    internal abstract fun createDefinition(owner: SubGraphDefinition, name: String): TDefinition
}
