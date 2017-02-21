# Object Cycles

Object cycles should be avoided if possible, but in some cases it is impossible to do so. OOverkommelig allows you to define object graphs with cycles.

To be able to create cycles the creation of objects is split in 2 stages: creation and wiring. During creation the critical dependencies must be set, and during wiring the other dependencies can be set.

See the three object definitions in `TypeSgd` to see how they are wired in a cycle.

The type of each least one object definition in a cycle must be declared, because the type inference of Kotlin cannot determine types when cycles without type declarations exist. See the definition of `mainMenu` in `TypesSgd`.  
