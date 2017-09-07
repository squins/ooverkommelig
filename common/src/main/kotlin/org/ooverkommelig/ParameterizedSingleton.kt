package org.ooverkommelig

@Deprecated(message = "Confusing term as singletons can also be created using other definition types.",
        replaceWith = ReplaceWith("ParameterizedOnce", "org.ooverkommelig.ParameterizedOnce"))
class ParameterizedSingleton<TObject, in TParameter>(create: (TParameter) -> TObject) :
        ParameterizedOnce<TObject, TParameter>(create)
