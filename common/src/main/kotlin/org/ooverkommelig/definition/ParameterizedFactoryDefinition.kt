package org.ooverkommelig.definition

import org.ooverkommelig.ParameterizedFactory
import org.ooverkommelig.SubGraphDefinition

internal class ParameterizedFactoryDefinition<TObject, in TParameter>(
        override val owner: SubGraphDefinition,
        override val name: String,
        override val delegate: ParameterizedFactory<TObject, TParameter>) :
        ParameterizedDefinition<TObject, TParameter>(),
        ObjectCreatingDefinition<TObject> {
    override val type = "factory"

    override fun create(argument: TParameter) = handleCreation(argument) { delegate.create(argument) }
}
