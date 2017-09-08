package org.ooverkommelig.definition

import org.ooverkommelig.Definition

internal interface DelegateOfObjectToCreateEagerly<TObject> {
    fun getValue(): Definition<TObject>
}
