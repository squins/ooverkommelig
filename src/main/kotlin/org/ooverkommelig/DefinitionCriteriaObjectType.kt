package org.ooverkommelig

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.WildcardType
import kotlin.reflect.KType
import kotlin.reflect.KTypeProjection
import kotlin.reflect.KVariance
import kotlin.reflect.full.createType

// http://stackoverflow.com/questions/36253310/how-to-get-actual-type-arguments-of-a-reified-generic-parameter-in-kotlin
inline fun <reified TType : Any> t() = object : TypeReference<TType>() {}

abstract class TypeReference<TType> {
    val kotlinType = toKotlinType((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0])

    override fun toString() = kotlinType.toString()
}

private fun toKotlinType(javaType: Type, typeArguments: List<KType> = emptyList()): KType {
    return when (javaType) {
        is Class<*> ->
            javaType.kotlin.createType(typeArguments.map { KTypeProjection(KVariance.INVARIANT, it) })
        is ParameterizedType ->
            toKotlinType(javaType.rawType, javaType.actualTypeArguments.map { toKotlinType(it) })
        is WildcardType ->
            toKotlinType(javaType.upperBounds[0], typeArguments)
        else ->
            throw IllegalArgumentException("Cannot convert Java type: $javaType")
    }
}
