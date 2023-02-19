package org.ooverkommelig.jvmreflect.retrievabledefinitions

import kotlin.reflect.jvm.ExperimentalReflectionOnLambdas
import kotlin.reflect.jvm.reflect

@Suppress("UNCHECKED_CAST")
fun <T> t() = null as T

@OptIn(ExperimentalReflectionOnLambdas::class)
internal fun <T> (() -> T).asKType() = reflect()?.returnType ?: throw IllegalStateException()
