package org.ooverkommelig

interface ObjectType<TType> {
    fun asKType() = this::class.supertypes[0].arguments[0].type ?: throw IllegalStateException("Unable to determine the type.")
}

typealias t<TType> = ObjectType<TType>
