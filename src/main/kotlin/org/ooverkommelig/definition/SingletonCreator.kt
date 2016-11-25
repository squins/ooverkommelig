package org.ooverkommelig.definition

internal class SingletonCreator<out TObject>(private val definition: ObjectCreatingDefinition<TObject>, creator: () -> TObject) {
    private val lazyValue = lazy(creator)

    internal fun getOrCreate() =
            if (lazyValue.isInitialized()) {
                lazyValue.value
            } else {
                definition.handleCreation { lazyValue.value }
            }
}
