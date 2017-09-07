package org.ooverkommelig

import org.ooverkommelig.definition.ObjectCreatingDefinitionDelegate
import org.ooverkommelig.definition.ParameterizedAlwaysDefinition
import org.ooverkommelig.definition.ParameterizedDefinition

open class ParameterizedAlways<TObject, in TParameter>(internal val create: (TParameter) -> TObject) :
        ObjectCreatingDefinitionDelegate<ParameterizedDefinition<TObject, TParameter>, TObject>() {
    override fun createDefinition(owner: SubGraphDefinition, name: String): ParameterizedDefinition<TObject, TParameter> = ParameterizedAlwaysDefinition(owner, name, this)
}