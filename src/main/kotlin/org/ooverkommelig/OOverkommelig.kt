package org.ooverkommelig

fun <TValue : Any> req(definition: Definition<TValue>) = definition.get()

fun <TValue> opt(definition: Definition<TValue>) = definition.get()

inline fun <reified TInterface : Any> i(): Class<TInterface> {
    val result = TInterface::class.java

    check(result.isInterface, { "Type: ${result.name}, is not an interface." })

    return result
}
