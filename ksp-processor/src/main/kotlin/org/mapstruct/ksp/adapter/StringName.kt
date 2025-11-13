package org.mapstruct.ksp.adapter

import javax.lang.model.element.Name

class StringName(val name: String) : Name {
    override fun contentEquals(cs: CharSequence?): Boolean {
        return name == cs.toString()
    }

    override val length: Int = name.length

    override fun get(index: Int): Char = name[index]

    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence =
        name.subSequence(startIndex, endIndex)
}