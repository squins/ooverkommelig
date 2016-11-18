package org.ooverkommelig.definition

import org.ooverkommelig.Definition

interface WiringContext<out TObject> {
    fun <TOther : Any> toReq(otherDefinition: Definition<TOther>, wirer: (TObject, TOther) -> Unit)
    fun <TOther> toOpt(otherDefinition: Definition<TOther>, wirer: (TObject, TOther) -> Unit)
}
