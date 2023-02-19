package org.ooverkommelig.definition

import org.ooverkommelig.Definition

abstract class AspectDelegate<TInterface : Any> :
    ObjectCreatingDefinitionSelfProvidingDelegate<AspectDefinition<TInterface>, TInterface>() {
    internal abstract val create: (Class<TInterface>, Definition<TInterface>) -> TInterface

    internal val functions = AspectFunctions<TInterface>()

    fun before(before: (TInterface) -> Unit): AspectDelegate<TInterface> {
        functions.beforeFunction = before
        return this
    }

    fun afterSuccess(afterSuccess: (TInterface) -> Unit): AspectDelegate<TInterface> {
        functions.afterSuccessFunction = afterSuccess
        return this
    }

    fun afterException(afterException: (TInterface) -> Unit): AspectDelegate<TInterface> {
        functions.afterExceptionFunction = afterException
        return this
    }

    fun after(after: (TInterface) -> Unit): AspectDelegate<TInterface> {
        functions.afterInvocationFunction = after
        return this
    }
}
