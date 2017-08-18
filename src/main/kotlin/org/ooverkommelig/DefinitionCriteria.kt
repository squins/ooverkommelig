package org.ooverkommelig

class DefinitionCriteria<TObject>(private val functionWithResultDefininingType: () -> TObject, val mustReturnSameObjectForAllRetrievals: Boolean = false) {
    fun getType() = functionWithResultDefininingType.asKType()
}
