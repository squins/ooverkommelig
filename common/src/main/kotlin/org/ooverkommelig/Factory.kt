package org.ooverkommelig

@Deprecated(message = "Confusing term as this definition type could also result in singletons.",
        replaceWith = ReplaceWith("Always", "org.ooverkommelig.Always"))
class Factory<TObject>(create: () -> TObject) : Always<TObject>(create)
