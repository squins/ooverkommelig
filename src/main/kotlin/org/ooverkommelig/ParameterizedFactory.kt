package org.ooverkommelig

@Deprecated(message = "Confusing term as this definition type could also result in singletons.",
        replaceWith = ReplaceWith("ParameterizedAlways", "org.ooverkommelig.ParameterizedAlways"))
class ParameterizedFactory<TObject, in TParameter>(create: (TParameter) -> TObject) :
        ParameterizedAlways<TObject, TParameter>(create)
