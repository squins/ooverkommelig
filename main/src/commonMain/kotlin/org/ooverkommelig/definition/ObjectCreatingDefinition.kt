package org.ooverkommelig.definition

import org.ooverkommelig.SubGraphDefinition

internal interface ObjectCreatingDefinition<TObject> : NamedDefinition<TObject> {
    val owner: SubGraphDefinition
    val delegate: ObjectCreatingDefinitionDelegate<*, TObject>

    fun handleCreation(argument: Any? = null, creator: () -> TObject) = owner.handleCreation(this, argument, creator)

    fun wire(obj: TObject) {
        delegate.wiringFunction(obj)
    }

    fun init(obj: TObject) {
        delegate.initializationFunction(obj)
    }

    fun dispose(obj: TObject) {
        delegate.disposalFunction(obj)
    }
}
