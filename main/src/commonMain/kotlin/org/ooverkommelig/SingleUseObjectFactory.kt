package org.ooverkommelig

expect abstract class SingleUseObjectFactory<out TObject : Any>(objectGraphConfiguration: ObjectGraphConfiguration) :
    SingleUseObjectFactoryCommon<TObject> {
    fun <TResult> createAndUse(block: (TObject) -> TResult): TResult
}
