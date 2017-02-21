# Object Lifecycles

The lifecycle of object definitions is very simple. Object lifecycle steps are divided in 2 groups: graph creation and disposal:

* Graph creation
    * Create: Instantiate/retrieve/... the object and ensure that it has its critical dependencies. All dependencies retrieved at this point are guaranteed to have been created. Creation of cycles is not allowed here, and doing so will result in an exception showing the cycle that was created.
    * `wire`: Make additional object connections (usually non-critical for the object, but critical for the application). All dependencies retrieved previously and here, are guaranteed to have been created. Creating cycles between objects must be done here, and is the primary purpose of this step.
    * `init`: Used to initialize objects that need additional actions before they functions as intended. All dependencies retrieved previously and here, are guaranteed to have been wired. Note that a service that is being started can have services that have not been started yet as dependencies.
* Graph disposal
    * `dispose`: Used to tell objects that they need to release resources. Note that other services having a dependency on a disposed-of object, may invoke the object after it released the resources it needs for its operation. Objects are disposed of in reverse order of creation/initialization.

OOverkommelig does not impose any requirements (method names, implemented interfaces, annotations, etc.) on objects to be able to initialize or dispose of them. This means that lifecycles can be made to work, but also means that lifecycle steps must be invoked explicitly, i.e. methods marked as initialization or disposal methods (e.g. using annotations) will never be invoked by OOverkommelig.

This example shows how an object can be initialized and disposed of. It also illustrates the order in which lifecycle methods of objects depending on each other are invoked.

The other lifecycle methods are already used in other examples: All examples show how to create object, and the cycles example shows how to wire objects.
