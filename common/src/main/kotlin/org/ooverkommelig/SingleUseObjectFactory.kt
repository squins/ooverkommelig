package org.ooverkommelig

header abstract class SingleUseObjectFactory<out TObject : Any>(provided: ProvidedBase, objectGraphConfiguration: ObjectGraphConfiguration) : SingleUseObjectFactoryCommon<TObject> {
    fun <TResult> createAndUse(block: (TObject) -> TResult): TResult
}
