package org.ooverkommelig.definition

import org.ooverkommelig.Definition

abstract class ParameterizedDefinition<out TObject, in TParameter> {
    internal abstract val type: String

    internal abstract val name: String

    operator fun invoke(argument: TParameter): Definition<TObject> = ArgumentBoundParameterizedDefinition(this, argument)

    internal abstract fun create(argument: TParameter): TObject

    override fun toString() = "Parameterized $type definition $name"
}
