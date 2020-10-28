package org.ooverkommelig.jvmreflect.retrievabledefinitions

import kotlin.reflect.jvm.reflect

@Suppress("CAST_NEVER_SUCCEEDS", "UNCHECKED_CAST")
fun <T> t() = null as T

internal fun <T> (() -> T).asKType() =
        reflect()?.returnType ?: throw IllegalStateException()
