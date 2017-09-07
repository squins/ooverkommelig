package org.ooverkommelig

@Deprecated(message = "Confusing term as singletons can also be created using other definition types.",
        replaceWith = ReplaceWith("AspectOnce", "org.ooverkommelig.AspectOnce"))
class AspectSingleton<TInterface>(create: (Class<TInterface>, Definition<TInterface>) -> TInterface) :
        AspectOnce<TInterface>(create)
