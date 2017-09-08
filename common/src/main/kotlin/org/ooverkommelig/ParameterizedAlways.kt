package org.ooverkommelig

import org.ooverkommelig.definition.ObjectCreatingDefinitionSelfProvidingDelegate
import org.ooverkommelig.definition.ParameterizedAlwaysDefinition
import org.ooverkommelig.definition.ParameterizedDefinition

open class ParameterizedAlways<TObject, in TParameter>(internal val create: (TParameter) -> TObject) :
        ObjectCreatingDefinitionSelfProvidingDelegate<ParameterizedDefinition<TObject, TParameter>, TObject>() {
    override fun createDefinition(owner: SubGraphDefinition, name: String): ParameterizedDefinition<TObject, TParameter> = ParameterizedAlwaysDefinition(owner, name, this)
}