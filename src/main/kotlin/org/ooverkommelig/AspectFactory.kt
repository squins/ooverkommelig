package org.ooverkommelig

class AspectFactory<TInterface>(override val create: (Class<TInterface>, org.ooverkommelig.Definition<TInterface>) -> TInterface) :
        org.ooverkommelig.definition.AspectDelegate<TInterface>() {
    override fun createDefinition(owner: org.ooverkommelig.SubGraphDefinition, name: String) = org.ooverkommelig.definition.AspectFactoryDefinition<TInterface>(owner, name, this)
}
