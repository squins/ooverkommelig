package org.ooverkommelig

abstract class SingleUseObjectFactoryCommon<out TObject : Any>(provided: ProvidedBase, objectGraphConfiguration: ObjectGraphConfiguration) : ObjectGraphDefinition(provided, objectGraphConfiguration) {
    protected inner class Graph : DefinitionObjectGraph() {
        fun getObject() = req(objectDefinition)
    }

    protected abstract val objectDefinition: Definition<TObject>
}
