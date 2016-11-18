package org.ooverkommelig

import org.ooverkommelig.definition.DefinitionDelegate
import kotlin.reflect.KProperty

class Constant<out TObject>(private val value: TObject) : DefinitionDelegate<Definition<TObject>>() {
    override fun createDefinition(owner: SubGraphDefinition, name: String): Definition<TObject> = ConstantDefinition(value)

    operator fun getValue(any: Any, property: KProperty<*>): Definition<TObject> {
        return ConstantDefinition(value)
    }
}
