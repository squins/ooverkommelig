package org.ooverkommelig

abstract class SingleUseObjectFactory<out TObject : Any>(objectGraphConfiguration: ObjectGraphConfiguration = ObjectGraphConfiguration()) : ObjectGraphDefinition(objectGraphConfiguration) {
    fun <TResult> createAndUse(block: (TObject) -> TResult) {
        Graph().use { graph ->
            block(graph.getObject())
        }
    }

    private inner class Graph : DefinitionObjectGraph() {
        fun getObject() = req(objectDefinition)
    }

    protected abstract val objectDefinition: Definition<TObject>
}
