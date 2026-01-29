/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.kotlin.data

/**
 * @author Filip Hrisafov
 */
data class MultiConstructorProperty(var firstName: String?, var lastName: String?, var displayName: String?) {
    constructor(firstName: String?, lastName: String?) : this(firstName, lastName, null)
}
