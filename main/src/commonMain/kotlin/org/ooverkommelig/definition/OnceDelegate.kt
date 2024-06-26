package org.ooverkommelig.definition

import org.ooverkommelig.Definition
import org.ooverkommelig.DefinitionCreationContext
import org.ooverkommelig.SubGraphDefinition
import kotlin.reflect.KProperty

// The sub graph definition is passed to the constructor because otherwise "SubGraphDefinitionCommon" has to cast itself
// to "SubGraphDefinition", and then the casted itself to "getValue(SubGraphDefinition)".
internal class OnceDelegate<TObject>(
    private val owner: SubGraphDefinition,
    private val propertyName: String,
    internal val create: DefinitionCreationContext.() -> TObject
) :
    ObjectCreatingDefinitionDelegate<Definition<TObject>, TObject>(),
    DelegateOfObjectToCreateEagerly<TObject> {
    override fun registerPropertyIfNeeded(owner: SubGraphDefinition, property: KProperty<*>) {
        owner.addDefinitionProperty(property, true)
    }

    override fun createDefinition(owner: SubGraphDefinition, name: String, context: DefinitionCreationContext): Definition<TObject> =
        OnceDefinition(owner, name, this, context)

    override fun getValue() = getValue(owner, propertyName)
}
