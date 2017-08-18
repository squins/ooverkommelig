package org.ooverkommelig

class DefinitionCriteria<TObject>(private val functionWithTypeDefiningResult: () -> TObject, val mustReturnSameObjectForAllRetrievals: Boolean = false) {
    fun getType() = functionWithTypeDefiningResult.asKType()
}
