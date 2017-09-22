package org.ooverkommelig

impl abstract class SingleUseObjectFactory<out TObject : Any> impl constructor(provided: ProvidedBase, objectGraphConfiguration: ObjectGraphConfiguration) : SingleUseObjectFactoryCommon<TObject>(provided, objectGraphConfiguration) {
    constructor (provided: ProvidedBase) : this(provided, ObjectGraphConfiguration())
    
    impl fun <TResult> createAndUse(block: (TObject) -> TResult) =
            Graph().use { graph ->
                block(graph.getObject())
            }
}
