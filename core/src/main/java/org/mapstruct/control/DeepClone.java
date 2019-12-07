/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.control;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.mapstruct.util.Experimental;

/**
 * Controls the mapping, allows for a direct mapping from source type to target type.
 *
 * Clones a source type to a target type (assuming source and target are exactly the same.
 *
 * @author Sjaak Derksen
 *
 * @since 1.4
 */
@Retention(RetentionPolicy.CLASS)
@Experimental
@AllowByMappingMethod
public @interface DeepClone {
}
