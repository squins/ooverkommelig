package org.ooverkommelig.definition

import org.ooverkommelig.Definition

abstract class AspectDelegate<TInterface> : ObjectCreatingDefinitionDelegate<AspectDefinition<TInterface>, TInterface>() {
    internal abstract val create: (Class<TInterface>, Definition<TInterface>) -> TInterface
    internal var validateInvocationFunction: (Any) -> Unit = { }
    internal var afterSuccessFunction: (Any) -> Unit = { }
    internal var afterExceptionFunction: (Any) -> Unit = { }
    internal var afterInvocationFunction: (Any) -> Unit = { }

    fun before(validate: (Any) -> Unit): AspectDelegate<TInterface> {
        validateInvocationFunction = validate
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
