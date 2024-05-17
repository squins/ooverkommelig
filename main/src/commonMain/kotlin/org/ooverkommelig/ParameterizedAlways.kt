package org.ooverkommelig

import org.ooverkommelig.definition.ObjectCreatingDefinitionSelfProvidingDelegate
import org.ooverkommelig.definition.ParameterizedAlwaysDefinition
import org.ooverkommelig.definition.ParameterizedDefinition

class ParameterizedAlways<TObject, in TParameter>(internal val create: DefinitionCreationContext.(TParameter) -> TObject) :
    ObjectCreatingDefinitionSelfProvidingDelegate<ParameterizedDefinition<TObject, TParameter>, TObject>() {
    override fun createDefinition(
        owner: SubGraphDefinition,
        name: String,
        context: DefinitionCreationContext
    ): ParameterizedDefinition<TObject, TParameter> = ParameterizedAlwaysDefinition(owner, name, this, context)
}