package org.ooverkommelig

impl abstract class SingleUseObjectFactory<out TObject : Any>(provided: ProvidedBase, objectGraphConfiguration: ObjectGraphConfiguration) : SingleUseObjectFactoryCommon<TObject>(provided, objectGraphConfiguration) {
    impl fun <TResult> createAndUse(block: (TObject) -> TResult) =
            Graph().use { graph ->
                block(graph.getObject())
            }
}
