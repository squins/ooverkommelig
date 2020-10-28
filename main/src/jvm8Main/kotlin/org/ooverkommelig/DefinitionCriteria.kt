package org.ooverkommelig

import kotlin.reflect.KType

interface DefinitionCriteria<TObject> {
    val functionWithTypeDefiningResult: () -> TObject
    val mustReturnSameObjectForAllRetrievals: Boolean
    fun getType(): KType
}
