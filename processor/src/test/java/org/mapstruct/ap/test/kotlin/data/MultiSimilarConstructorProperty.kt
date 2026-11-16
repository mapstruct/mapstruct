/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.kotlin.data

/**
 * @author Filip Hrisafov
 */
data class PrimaryString(var firstName: String?, var lastName: String?, var displayName: String?) {
    constructor(firstName: String?, lastName: String?, age: Int) : this(firstName, lastName, null as String?)
}

data class PrimaryInt(var firstName: String?, var lastName: String?, var age: Int) {
    constructor(firstName: String?, lastName: String?, displayName: String?) : this(firstName, lastName, -1)
}

data class PrimaryLong(var firstName: String?, var lastName: String?, var age: Long) {
    constructor(firstName: String?, lastName: String?, displayName: String?) : this(firstName, lastName, -1)
}

data class PrimaryBoolean(var firstName: String?, var lastName: String?, var active: Boolean) {
    constructor(firstName: String?, lastName: String?, displayName: String?) : this(firstName, lastName, false)
}

data class PrimaryByte(var firstName: String?, var lastName: String?, var b: Byte) {
    constructor(firstName: String?, lastName: String?, displayName: String?) : this(firstName, lastName, 0)
}

data class PrimaryShort(var firstName: String?, var lastName: String?, var age: Short) {
    constructor(firstName: String?, lastName: String?, displayName: String?) : this(firstName, lastName, 0)
}

data class PrimaryChar(var firstName: String?, var lastName: String?, var c: Char) {
    constructor(firstName: String?, lastName: String?, displayName: String?) : this(firstName, lastName, 'a')
}

data class PrimaryFloat(var firstName: String?, var lastName: String?, var price: Float) {
    constructor(firstName: String?, lastName: String?, displayName: String?) : this(firstName, lastName, 0.0f)
}

data class PrimaryDouble(var firstName: String?, var lastName: String?, var price: Double) {
    constructor(firstName: String?, lastName: String?, displayName: String?) : this(firstName, lastName, 0.0)
}

@Suppress("ArrayInDataClass")
data class PrimaryArray(var firstName: String?, var lastName: String?, var elements: Array<String>) {
    constructor(firstName: String?, lastName: String?, displayName: String?) : this(firstName, lastName, emptyArray())
}
