/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.kotlin.data

data class CustomerWithOptionalParametersDto(
    val name: String? = null,
    val email: String? = null
) {
    constructor(name: String) : this(name, null)
}
