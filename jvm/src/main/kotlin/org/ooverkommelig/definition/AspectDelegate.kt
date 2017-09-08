package org.ooverkommelig.definition

import org.ooverkommelig.Definition

abstract class AspectDelegate<TInterface> : ObjectCreatingDefinitionSelfProvidingDelegate<AspectDefinition<TInterface>, TInterface>() {
    internal abstract val create: (Class<TInterface>, Definition<TInterface>) -> TInterface
    internal var beforeFunction: (Any) -> Unit = { }
    internal var afterSuccessFunction: (Any) -> Unit = { }
    internal var afterExceptionFunction: (Any) -> Unit = { }
    internal var afterInvocationFunction: (Any) -> Unit = { }

    fun before(before: (Any) -> Unit): AspectDelegate<TInterface> {
        beforeFunction = before
        return this
    }

    fun afterSuccess(afterSuccess: (Any) -> Unit): AspectDelegate<TInterface> {
        afterSuccessFunction = afterSuccess
        return this
    }

    fun afterException(afterException: (Any) -> Unit): AspectDelegate<TInterface> {
        afterExceptionFunction = afterException
        return this
    }

    fun after(after: (Any) -> Unit): AspectDelegate<TInterface> {
        afterInvocationFunction = after
        return this
    }
}
