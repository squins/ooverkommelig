package org.ooverkommelig

import kotlin.reflect.jvm.reflect

@Suppress("CAST_NEVER_SUCCEEDS", "UNCHECKED_CAST")
fun <T> t() = null as T

fun <T> (() -> T).asKType() =
        reflect()?.returnType ?: throw IllegalStateException()
