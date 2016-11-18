package org.ooverkommelig.definition

import org.ooverkommelig.Definition

abstract class AspectDelegate<TInterface> : ObjectCreatingDefinitionDelegate<AspectDefinition<TInterface>, TInterface>() {
    internal abstract val create: (Class<TInterface>, Definition<TInterface>) -> TInterface
    internal var beforeInvocationFunction: (Any) -> Boolean = { true }
    internal var afterSuccessFunction: (Any) -> Unit = { }
    internal var afterExceptionFunction: (Any) -> Unit = { }
    internal var afterInvocationFunction: (Any) -> Unit = { }

    fun before(before: (Any) -> Unit): AspectDelegate<TInterface> {
        beforeInvocationFunction = { wrapped ->
            before(wrapped)
            true
        }
        return this
    }

    fun beforeGate(beforeGate: (Any) -> Boolean): AspectDelegate<TInterface> {
        beforeInvocationFunction = beforeGate
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
