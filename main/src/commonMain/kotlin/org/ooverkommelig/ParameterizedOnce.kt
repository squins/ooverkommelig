package org.ooverkommelig

import org.ooverkommelig.definition.ObjectCreatingDefinitionSelfProvidingDelegate
import org.ooverkommelig.definition.ParameterizedDefinition
import org.ooverkommelig.definition.ParameterizedOnceDefinition

class ParameterizedOnce<TObject, in TParameter>(internal val create: DefinitionCreationContext.(TParameter) -> TObject) :
    ObjectCreatingDefinitionSelfProvidingDelegate<ParameterizedDefinition<TObject, TParameter>, TObject>() {
    override fun createDefinition(
        owner: SubGraphDefinition,
        name: String,
        context: DefinitionCreationContext
    ): ParameterizedDefinition<TObject, TParameter> = ParameterizedOnceDefinition(owner, name, this, context)
}