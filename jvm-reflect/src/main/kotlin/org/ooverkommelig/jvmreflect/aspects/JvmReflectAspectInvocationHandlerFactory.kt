package org.ooverkommelig.jvmreflect.aspects

import org.ooverkommelig.AspectInvocationHandlerFactory
import org.ooverkommelig.definition.AspectFunctions
import java.lang.reflect.InvocationHandler

object JvmReflectAspectInvocationHandlerFactory : AspectInvocationHandlerFactory {
    override fun <TInterface : Any> create(
        wrapped: TInterface,
        aspectFunctions: AspectFunctions<TInterface>
    ): InvocationHandler =
        JvmReflectAspectInvocationHandler(wrapped, aspectFunctions)
}
