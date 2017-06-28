package org.ooverkommelig

abstract class Definition<out TObject> {
    internal abstract fun get(): TObject
}
