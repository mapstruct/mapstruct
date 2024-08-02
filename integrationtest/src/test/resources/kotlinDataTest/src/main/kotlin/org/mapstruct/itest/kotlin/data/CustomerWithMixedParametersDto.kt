/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.kotlin.data

data class CustomerWithMixedParametersDto @Default constructor(
    val name: String,
    var age: Int,
    val email: String? = null
) {
    var job: String? = null

    constructor(name: String, email: String, job: String) : this(name, 0, email) {
        this.job = job
    }

    annotation class Default
}
