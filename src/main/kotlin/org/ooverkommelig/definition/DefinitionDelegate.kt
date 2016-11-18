package org.ooverkommelig.definition

import org.ooverkommelig.SubGraphDefinition
import kotlin.reflect.KProperty

abstract class DefinitionDelegate<out TDefinition> {
    private var definition: TDefinition? = null

    operator fun getValue(owner: SubGraphDefinition, property: KProperty<*>): TDefinition {
        val currentDefinition = definition ?: createDefinition(owner, "${owner.name}#${property.name}")
        definition = currentDefinition
        return currentDefinition
    }

    internal abstract fun createDefinition(owner: SubGraphDefinition, name: String): TDefinition
}
