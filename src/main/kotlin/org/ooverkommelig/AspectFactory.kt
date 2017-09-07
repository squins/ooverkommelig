package org.ooverkommelig

@Deprecated(message = "Confusing term as this definition type could also result in singletons.",
        replaceWith = ReplaceWith("AspectAlways", "org.ooverkommelig.AspectAlways"))
class AspectFactory<TInterface>(create: (Class<TInterface>, Definition<TInterface>) -> TInterface) :
        AspectAlways<TInterface>(create)
