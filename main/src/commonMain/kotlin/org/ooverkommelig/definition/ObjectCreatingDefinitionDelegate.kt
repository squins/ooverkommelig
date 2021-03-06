package org.ooverkommelig.definition

import org.ooverkommelig.SubGraphDefinition
import kotlin.reflect.KProperty

abstract class ObjectCreatingDefinitionDelegate<out TDefinition, TObject> : DefinitionDelegate<TDefinition>() {
    internal var wiringFunction: (TObject) -> Unit = {}
    internal var initializationFunction: (TObject) -> Unit = {}
    internal var disposalFunction: (TObject) -> Unit = {}

    fun wire(wire: (TObject) -> Unit): ObjectCreatingDefinitionDelegate<TDefinition, TObject> {
        wiringFunction = wire
        return this
    }

    fun init(init: (TObject) -> Unit): ObjectCreatingDefinitionDelegate<TDefinition, TObject> {
        initializationFunction = init
        return this
    }

    fun dispose(dispose: (TObject) -> Unit): ObjectCreatingDefinitionDelegate<TDefinition, TObject> {
        disposalFunction = dispose
        return this
    }

    internal open fun registerPropertyIfNeeded(owner: SubGraphDefinition, property: KProperty<*>) {
        // No action needed. By default the property is not registered.
    }
}
