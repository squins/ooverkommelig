package org.ooverkommelig

actual abstract class SingleUseObjectFactory<out TObject : Any> actual constructor(provided: ProvidedBase, objectGraphConfiguration: ObjectGraphConfiguration) : SingleUseObjectFactoryCommon<TObject>(provided, objectGraphConfiguration) {
    constructor (provided: ProvidedBase) : this(provided, ObjectGraphConfiguration())

    actual fun <TResult> createAndUse(block: (TObject) -> TResult) =
            Graph().run {
                try {
                    block(getObject())
                } finally {
                    close()
                }
            }
}
