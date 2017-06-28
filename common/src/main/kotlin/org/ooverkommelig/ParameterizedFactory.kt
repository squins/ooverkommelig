package org.ooverkommelig

import org.ooverkommelig.definition.ObjectCreatingDefinitionDelegate
import org.ooverkommelig.definition.ParameterizedDefinition
import org.ooverkommelig.definition.ParameterizedFactoryDefinition

class ParameterizedFactory<TObject, in TParameter>(internal val create: (TParameter) -> TObject) :
        ObjectCreatingDefinitionDelegate<ParameterizedDefinition<TObject, TParameter>, TObject>() {
    override fun createDefinition(owner: SubGraphDefinition, name: String): ParameterizedDefinition<TObject, TParameter> = ParameterizedFactoryDefinition(owner, name, this)
}