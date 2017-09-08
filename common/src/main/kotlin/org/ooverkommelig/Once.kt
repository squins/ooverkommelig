package org.ooverkommelig

import org.ooverkommelig.definition.OnceDelegate
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

open class Once<TObject>(internal val create: () -> TObject) {
    private var mustBeCreatedEagerly = false
    private var wiringFunction: (TObject) -> Unit = {}
    private var initializationFunction: (TObject) -> Unit = {}
    private var disposalFunction: (TObject) -> Unit = {}

    fun eager(): Once<TObject> {
        mustBeCreatedEagerly = true
        return this
    }

    fun wire(wire: (TObject) -> Unit): Once<TObject> {
        wiringFunction = wire
        return this
    }

    fun init(init: (TObject) -> Unit): Once<TObject> {
        initializationFunction = init
        return this
    }

    fun dispose(dispose: (TObject) -> Unit): Once<TObject> {
        disposalFunction = dispose
        return this
    }

    operator fun provideDelegate(owner: SubGraphDefinition, property: KProperty<*>): ReadOnlyProperty<SubGraphDefinition, Definition<TObject>> {
        val result = OnceDelegate(owner, property.name, create)
        result.wire(wiringFunction).init(initializationFunction).dispose(disposalFunction)
        result.registerPropertyIfNeeded(owner, property)
        if (mustBeCreatedEagerly) {
            owner.delegatesOfObjectsToCreateEagerly += result
        }
        return result
    }
}
