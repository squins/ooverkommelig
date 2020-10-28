package org.ooverkommelig

import org.ooverkommelig.definition.AspectFunctions
import java.lang.reflect.InvocationHandler

interface AspectInvocationHandlerFactory {
    fun <TInterface: Any> create(wrapped: TInterface, aspectFunctions: AspectFunctions<TInterface>) : InvocationHandler
}
