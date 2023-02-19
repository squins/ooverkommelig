package org.ooverkommelig

actual abstract class SingleUseObjectFactory<out TObject : Any> actual constructor(objectGraphConfiguration: ObjectGraphConfiguration) :
    SingleUseObjectFactoryCommon<TObject>(objectGraphConfiguration) {
    constructor () : this(ObjectGraphConfiguration())

    actual fun <TResult> createAndUse(block: (TObject) -> TResult) =
        Graph().use { graph ->
            block(graph.getObject())
        }
}
