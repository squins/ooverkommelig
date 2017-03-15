package org.ooverkommelig

import kotlin.reflect.KProperty

object ObjectType {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Nothing =
            throw IllegalStateException("Cannot get a value used to specify an object type for definition criteria.")
}
