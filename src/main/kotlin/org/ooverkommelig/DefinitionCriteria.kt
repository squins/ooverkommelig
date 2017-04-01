package org.ooverkommelig

class DefinitionCriteria<TObject>(val objectType: TypeReference<TObject>, val mustReturnSameObjectForAllRetrievals: Boolean = false)
