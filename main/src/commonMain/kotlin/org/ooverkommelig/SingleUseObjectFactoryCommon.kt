package org.ooverkommelig

abstract class SingleUseObjectFactoryCommon<out TObject : Any>(objectGraphConfiguration: ObjectGraphConfiguration) : ObjectGraphDefinition(objectGraphConfiguration) {
    protected inner class Graph : DefinitionObjectGraph() {
        fun getObject() = req(objectDefinition)
    }

    protected abstract val objectDefinition: Definition<TObject>
}
