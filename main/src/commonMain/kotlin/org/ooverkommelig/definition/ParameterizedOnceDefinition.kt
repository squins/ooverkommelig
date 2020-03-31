package org.ooverkommelig.definition

import org.ooverkommelig.ParameterizedOnce
import org.ooverkommelig.SubGraphDefinition

internal class ParameterizedOnceDefinition<TObject, in TParameter>(
        override val owner: SubGraphDefinition,
        override val name: String,
        override val delegate: ParameterizedOnce<TObject, TParameter>) :
        ParameterizedDefinition<TObject, TParameter>(),
        ObjectCreatingDefinition<TObject> {
    private val valueCreators = mutableMapOf<TParameter, OnceCreator<TObject>>()

    override val type = "once"

    override fun create(argument: TParameter): TObject {
        return (valueCreators[argument] ?: createCreator(argument)).getOrCreate()
    }

    private fun createCreator(argument: TParameter): OnceCreator<TObject> {
        val result = OnceCreator(this, argument) { delegate.create(argument) }
        valueCreators[argument] = result
        return result
    }
}
