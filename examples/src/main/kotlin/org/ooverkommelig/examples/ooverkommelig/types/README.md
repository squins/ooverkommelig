# Object Types: Multiple Instances and Implicit Casts

This example shows how OOverkommelig handles object types. Is is far easier to work with multiple instances of the same type, and to use objects as any type the object is an instance of, than most other dependency injection containers.  

With OOverkommelig creating multiple objects having the same type is not a problem at all. Nothing needs to be changed in the classes of the objects, no extra types have to be written and no additional syntax in the graph definition is needed to define or use the objects. Not having to touch the classes of which instances are created, means that using third-party code that is unaware of your environment is always possible.
 
You can use the same object definition for all types that the object is an instance of, i.e. there is no need to introduce artificial definitions just to cast the object to the type that is needed.

## Multiple Strings

`TypesSgd` defines multiple objects of type `String`: `javaVendor`, `javaVersion` and `jvmReport`. Both for developers and OOverkommelig it is easy to not confuse them, because they are accessed by reference instead of by type.

## Decorators

Multiple implementations of `ReportSender` are created in `TypesSgd`. Note that the type of the following object definitions is `Definition<ReportSender>`:

* `reportToStandardOutSender`
* `reportToMessageDialogSender`
* `compoundReportSender`
* `outgoingReportLoggingSender`

Again, finding out which object definition is used where is easy. For example: Starting at the definition for `mainRunnable` you can easily navigate the chain of `ReportSender` implementations by first going to the declaration of `outgoingReportLoggingSender`, and then continuing from there. As graphs become more complex and define dozens or hundreds of object, the ability to go to the definition of a dependency (and the reverse: finding usages of an object definition) becomes very important for understanding the graph that will be constructed.

## Implicit Casts

The definition of `notifyingReportSender` shows that only one definition is needed, no matter as what type the object is used. In the wiring of `outgoingReportLoggingSender`, `notifyingReportSender` is used as `NotifyingReportSender`, and in the creation of `mainRunnable` the same object definition is used as `ReportSender`.

This may seem logical, but with some type-based containers you have to add a definition for each type as which an object is used. For example (pseudo code):
 
     fun notifyingReportSneder() = NotifyingReportSender()
      
     fun notifyingReportSenderAsReportSender(nrs: NotifyingReportSender): ReportSender = nrs
