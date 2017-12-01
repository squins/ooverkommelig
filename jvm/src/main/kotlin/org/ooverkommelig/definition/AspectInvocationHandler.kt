package org.ooverkommelig.definition

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.reflect.jvm.javaMethod

internal class AspectInvocationHandler<TInterface>(
        private val wrapped: Any,
        private val delegate: AspectDelegate<TInterface>) : InvocationHandler {
    override fun invoke(proxy: Any, method: Method, optionalArguments: Array<out Any?>?): Any? {
        val actualArguments = optionalArguments ?: NO_ARGUMENTS

        return if (method == EQUALS_METHOD) {
            isNotNullAndAreProxyHandlerAndWrappedEqual(proxy, actualArguments[0])
        } else {
            invokeInterfaceFunctionIfAspectApproves(method, actualArguments)
        }
    }

    private fun isNotNullAndAreProxyHandlerAndWrappedEqual(invokedProxy: Any, other: Any?) = other != null && areProxyHandlerAndWrappedEqual(invokedProxy, other)

    private fun areProxyHandlerAndWrappedEqual(invokedProxy: Any, other: Any) =
            invokedProxy.javaClass == other.javaClass && areHandlerAndWrappedEqual(other)

    private fun areHandlerAndWrappedEqual(other: Any): Boolean {
        val handler = Proxy.getInvocationHandler(other)
        return handler is AspectInvocationHandler<*> && handler.wrapped == wrapped
    }

    private fun invokeInterfaceFunctionIfAspectApproves(method: Method, arguments: Array<out Any?>): Any? {
        delegate.beforeFunction(wrapped)

        return invoke(method, arguments)
    }

    private fun invoke(method: Method, arguments: Array<out Any?>): Any? {
        val result: Any?

        try {
            result = tryToInvoke(wrapped, method, arguments)
            delegate.afterSuccessFunction(wrapped)
        } catch (exception: Exception) {
            delegate.afterExceptionFunction(wrapped)
            throw exception
        } finally {
            delegate.afterInvocationFunction(wrapped)
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
