package org.ooverkommelig

import kotlin.reflect.KProperty
import kotlin.reflect.KType

class DefinitionCriteria<out TObject>(typeDefiningProperty: KProperty<TObject>, val mustReturnSameObjectForAllRetrievals: Boolean = false) {
    internal var definedType: KType = typeDefiningProperty.returnType
}
