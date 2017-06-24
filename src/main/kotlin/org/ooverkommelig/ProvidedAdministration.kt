package org.ooverkommelig

import kotlin.reflect.KClass

abstract class ProvidedAdministration : ProvidedBase {
    override val owners = mutableMapOf<KClass<*>, Any>()
}
