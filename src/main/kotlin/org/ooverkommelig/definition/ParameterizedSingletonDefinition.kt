package org.ooverkommelig.definition

import org.ooverkommelig.ParameterizedSingleton
import org.ooverkommelig.SubGraphDefinition

internal class ParameterizedSingletonDefinition<TObject, in TParameter>(
        override val owner: SubGraphDefinition,
        override val name: String,
        override val delegate: ParameterizedSingleton<TObject, TParameter>) :
        ParameterizedDefinition<TObject, TParameter>(),
        ObjectCreatingDefinition<TObject> {
    private val valueCreators = mutableMapOf<TParameter, SingletonCreator<TObject>>()

    override val type = "singleton"

    override fun create(argument: TParameter): TObject {
        return (valueCreators[argument] ?: createCreator(argument)).getOrCreate()
    }

    private fun createCreator(argument: TParameter): SingletonCreator<TObject> {
        val result = SingletonCreator(this, argument, { delegate.create(argument) })
        valueCreators[argument] = result
        return result
    }
}
