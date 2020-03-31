package org.ooverkommelig.definition

internal class OnceCreator<out TObject>(
        private val definition: ObjectCreatingDefinition<TObject>,
        private val argument: Any?,
        creator: () -> TObject) {
    private val lazyValue = lazy(creator)

    internal fun getOrCreate() =
            if (lazyValue.isInitialized()) {
                lazyValue.value
            } else {
                definition.handleCreation(argument) { lazyValue.value }
            }
}
