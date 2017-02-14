package org.ooverkommelig

import org.ooverkommelig.definition.ParameterizedDefinition

fun <TObject> providedByMe(): Definition<TObject> = throw notOverriddenInObjectException()

fun <TObject, TParameter> providedByMeParameterized(): ParameterizedDefinition<TObject, TParameter> = throw notOverriddenInObjectException()

private fun notOverriddenInObjectException() =
        IllegalStateException("Graph definition declaring that it provides an object definition, must override it in the 'Provided' object passed to the child or sub graph.")
