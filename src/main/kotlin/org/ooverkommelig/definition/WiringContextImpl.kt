package org.ooverkommelig.definition

import org.ooverkommelig.Definition
import org.ooverkommelig.opt
import org.ooverkommelig.req

internal class WiringContextImpl<out TObject>(private val obj: TObject) : WiringContext<TObject> {
    override fun <TOther : Any> toReq(otherDefinition: Definition<TOther>, wirer: (TObject, TOther) -> Unit) {
        wirer(obj, req(otherDefinition))
    }

    override fun <TOther> toOpt(otherDefinition: Definition<TOther>, wirer: (TObject, TOther) -> Unit) {
        opt(otherDefinition)?.let { other -> wirer(obj, other) }
    }
}
