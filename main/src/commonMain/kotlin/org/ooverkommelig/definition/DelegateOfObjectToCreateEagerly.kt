package org.ooverkommelig.definition

import org.ooverkommelig.Definition

internal interface DelegateOfObjectToCreateEagerly<out TObject> {
    fun getValue(): Definition<TObject>
}
