package org.ooverkommelig

fun <TValue : Any> req(definition: Definition<TValue>) = definition.get()

fun <TValue> opt(definition: Definition<TValue>) = definition.get()
