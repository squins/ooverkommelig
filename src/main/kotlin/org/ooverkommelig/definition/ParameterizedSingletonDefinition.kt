package org.ooverkommelig.definition

import org.ooverkommelig.ParameterizedSingleton
import org.ooverkommelig.SubGraphDefinition

internal class ParameterizedSingletonDefinition<TObject, in TParameter>(
        override val owner: SubGraphDefinition,
        override val name: String,
        override val delegate: ParameterizedSingleton<TObject, TParameter>) :
        ParameterizedDefinition<TObject, TParameter>(),
        ObjectCreatingDefinition<TObject> {
    private val values = mutableMapOf<TParameter, Lazy<TObject>>()

    override val type = "singleton"

    override fun create(argument: TParameter): TObject {
        return (values[argument] ?: createLazy(argument)).value
    }

    private fun createLazy(argument: TParameter): Lazy<TObject> {
        val result = lazy { handleCreation(argument) { delegate.create(argument) } }
        values[argument] = result
        return result
    }
}
