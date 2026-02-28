/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.kotlin.data

/**
 * @author Filip Hrisafov
 */
data class UnsignedProperty(val age: UInt?) {
    // Java-friendly secondary constructor
    constructor(age: Int?) : this(age?.toUInt())

    @JvmName("getAge")
    fun getAgeAsLong(): Long? = age?.toLong()
}
