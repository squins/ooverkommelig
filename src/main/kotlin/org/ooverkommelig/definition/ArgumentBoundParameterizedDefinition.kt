package org.ooverkommelig.definition

import org.ooverkommelig.Definition

internal class ArgumentBoundParameterizedDefinition<out TObject, TParameter>(
        private val delegatee: ParameterizedDefinition<TObject, TParameter>,
        private val argument: TParameter) :
        Definition<TObject>() {
    override fun get() = delegatee.create(argument)

    override fun toString() = "$delegatee with argument $argument"
}
