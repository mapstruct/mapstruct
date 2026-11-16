/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.kotlin.data

/**
 * @author Filip Hrisafov
 */
data class DefaultPropertySource(val default: Boolean, val identifier: String?)

class DefaultPropertyTarget(
    var default: Boolean,
    var identifier: String?
)
