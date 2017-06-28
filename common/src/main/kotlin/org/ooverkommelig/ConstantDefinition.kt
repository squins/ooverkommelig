package org.ooverkommelig

class ConstantDefinition<out TObject>(private val value: TObject) : Definition<TObject>() {
    override fun get() = value
}
