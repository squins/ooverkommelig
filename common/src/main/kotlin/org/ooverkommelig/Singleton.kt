package org.ooverkommelig

@Deprecated(message = "Confusing term as singletons can also be created using other definition types.",
        replaceWith = ReplaceWith("Once", "org.ooverkommelig.Once"))
class Singleton<TObject>(create: () -> TObject) : Once<TObject>(create)
