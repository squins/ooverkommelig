package org.ooverkommelig.definition

import org.ooverkommelig.ParameterizedAlways
import org.ooverkommelig.SubGraphDefinition

internal class ParameterizedAlwaysDefinition<TObject, in TParameter>(
        override val owner: SubGraphDefinition,
        override val name: String,
        override val delegate: ParameterizedAlways<TObject, TParameter>) :
        ParameterizedDefinition<TObject, TParameter>(),
        ObjectCreatingDefinition<TObject> {
    override val type = "always"

    override fun create(argument: TParameter) = handleCreation(argument) { delegate.create(argument) }
}
