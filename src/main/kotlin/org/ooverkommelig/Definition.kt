package org.ooverkommelig

abstract class Definition<out TObject> {
    internal abstract fun get(): TObject
}

typealias D<TObject> = Definition<TObject>
