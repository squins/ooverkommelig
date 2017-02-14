package org.ooverkommelig

abstract class ProvidedAdministration : ProvidedBase {
    override val owners = mutableMapOf<Class<*>, Any>()
}
