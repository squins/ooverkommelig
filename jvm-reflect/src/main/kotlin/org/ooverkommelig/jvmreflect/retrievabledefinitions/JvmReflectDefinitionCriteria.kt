package org.ooverkommelig.jvmreflect.retrievabledefinitions

import org.ooverkommelig.DefinitionCriteria

class JvmReflectDefinitionCriteria<TObject>(override val functionWithTypeDefiningResult: () -> TObject, override val mustReturnSameObjectForAllRetrievals: Boolean = false) : DefinitionCriteria<TObject> {
    override fun getType() = functionWithTypeDefiningResult.asKType()
}
