package org.ooverkommelig.jvmreflect.aspects

import org.ooverkommelig.definition.AspectFunctions
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.reflect.jvm.javaMethod

internal class JvmReflectAspectInvocationHandler<TInterface : Any>(
    private val wrapped: TInterface,
    private val aspectFunctions: AspectFunctions<TInterface>
) : InvocationHandler {
    override fun invoke(proxy: Any, method: Method, optionalArguments: Array<out Any?>?): Any? {
        val actualArguments = optionalArguments ?: NO_ARGUMENTS

        return if (method == EQUALS_METHOD) {
            isNotNullAndAreProxyHandlerAndWrappedEqual(proxy, actualArguments[0])
        } else {
            invokeInterfaceFunctionIfAspectApproves(method, actualArguments)
        }
    }

    private fun isNotNullAndAreProxyHandlerAndWrappedEqual(invokedProxy: Any, other: Any?) =
        other != null && areProxyHandlerAndWrappedEqual(invokedProxy, other)

    private fun areProxyHandlerAndWrappedEqual(invokedProxy: Any, other: Any) =
        invokedProxy.javaClass == other.javaClass && areHandlerAndWrappedEqual(other)

    private fun areHandlerAndWrappedEqual(other: Any): Boolean {
        val handler = Proxy.getInvocationHandler(other)
        return handler is JvmReflectAspectInvocationHandler<*> && handler.wrapped == wrapped
    }

    private fun invokeInterfaceFunctionIfAspectApproves(method: Method, arguments: Array<out Any?>): Any? {
        aspectFunctions.beforeFunction(wrapped)

        return invoke(method, arguments)
    }

    private fun invoke(method: Method, arguments: Array<out Any?>): Any? {
        val result: Any?

        try {
            result = tryToInvoke(wrapped, method, arguments)
            aspectFunctions.afterSuccessFunction(wrapped)
        } catch (exception: Exception) {
            aspectFunctions.afterExceptionFunction(wrapped)
            throw exception
        } finally {
            aspectFunctions.afterInvocationFunction(wrapped)
        }

        return result
    }

    private fun tryToInvoke(wrapped: Any, method: Method, arguments: Array<out Any?>) =
        method.invoke(wrapped, *arguments)

    companion object {
        private val NO_ARGUMENTS = emptyArray<Any>()

        private val EQUALS_METHOD = Any::equals.javaMethod ?: throw IllegalStateException()
    }
}
