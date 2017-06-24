package org.ooverkommelig

import kotlin.reflect.KClass

interface ProvidedBase {
    val owners: MutableMap<KClass<*>, Any>

    fun setOwner(owner: Any) {
        owners[owner::class] = owner
    }
}

inline fun <reified TOwner : Any> ProvidedBase.owner() =
        owners[TOwner::class] as? TOwner ?: throw IllegalStateException("Owner has not been initialized.")
