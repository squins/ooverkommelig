package org.ooverkommelig

import org.ooverkommelig.definition.DefinitionDelegate
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class Constant<out TObject>(private val value: TObject) : DefinitionDelegate<Definition<TObject>>() {
    operator fun provideDelegate(owner: SubGraphDefinition, property: KProperty<*>): ReadOnlyProperty<SubGraphDefinition, Definition<TObject>> {
        owner.addDefinitionProperty(property, true)
        return this
    }

    override fun createDefinition(owner: SubGraphDefinition, name: String): Definition<TObject> = ConstantDefinition(value)

    operator fun getValue(any: Any, property: KProperty<*>): Definition<TObject> {
        return ConstantDefinition(value)
    }
}
