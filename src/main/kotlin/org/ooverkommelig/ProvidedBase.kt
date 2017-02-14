package org.ooverkommelig

interface ProvidedBase {
    val owners: MutableMap<Class<*>, Any>

    fun setOwner(owner: Any) {
        owners[owner.javaClass] = owner
    }
}

inline fun <reified TOwner : Any> ProvidedBase.owner() =
        owners[TOwner::class.java] as? TOwner ?: throw IllegalStateException("Owner has not been initialized.")
