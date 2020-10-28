package org.ooverkommelig.definition

class AspectFunctions<TInterface: Any> internal constructor() {
    var beforeFunction: (TInterface) -> Unit = { }
    var afterSuccessFunction: (TInterface) -> Unit = { }
    var afterExceptionFunction: (TInterface) -> Unit = { }
    var afterInvocationFunction: (TInterface) -> Unit = { }
}
